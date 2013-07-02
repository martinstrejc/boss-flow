/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.wicketstuff.boss.flow.processor.ext;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.condition.IFlowMatchedSwitchProcessor;
import cz.wicketstuff.boss.flow.util.FlowMatcherHelper;
import cz.wicketstuff.boss.flow.util.FlowStringUtils;

public abstract class FilteredStateSwitchProcessor<T extends Serializable> implements IFlowMatchedSwitchProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStateSwitchProcessor.class);

	private Pattern switchExpressionPattern;
	
	private Pattern stateNamePattern;
	
	private Pattern categoryNamePattern;
	
	private Class<? extends IFlowState> type;

	public FilteredStateSwitchProcessor() {
	}

	
	public FilteredStateSwitchProcessor(String switchExpressionRegex,
			String stateNameRegex, 
			String categoryNameRegex, 
			Class<? extends IFlowState> type) {
		this(FlowMatcherHelper.strigToPattern(switchExpressionRegex), 
				FlowMatcherHelper.strigToPattern(stateNameRegex), 
				FlowMatcherHelper.strigToPattern(categoryNameRegex), type);
	}

	public FilteredStateSwitchProcessor(Pattern switchExpressionPattern,
			Pattern stateNamePattern, Pattern categoryNamePattern,
			Class<? extends IFlowState> type) {
		this.switchExpressionPattern = switchExpressionPattern;
		this.categoryNamePattern = categoryNamePattern;
		this.stateNamePattern = stateNamePattern;
		this.type = type;
	}

	@Override
	public boolean match(String switchExpression, IFlowCarter<T> flow) {
		if(log.isDebugEnabled()) {
			log.debug("match switch expression: " + switchExpression + " on " + toString());
		}
		if(log.isTraceEnabled()) {
			log.trace(flow.toString());
		}
		boolean ret = matchExpressionName(switchExpression) && matchStateName(flow.getCurrentState().getStateName()) && matchType(flow.getCurrentState()); 
		if(log.isDebugEnabled()) {
			log.debug("match result: " + ret);
		}
		return ret;
	}

	public boolean matchExpressionName(String switchExpression) {
		return this.switchExpressionPattern == null || switchExpressionPattern.matcher(switchExpression).matches();
	}

	public boolean matchStateName(String stateName) {
		return this.stateNamePattern == null || stateNamePattern.matcher(stateName).matches();
	}

	
	public boolean matchType(IFlowState implementingObject) {
		return this.type == IFlowState.class || this.type.isInstance(implementingObject);
	}

	public String getSwitchExpressionRegex() {
		return FlowStringUtils.safeToString(switchExpressionPattern);
	}

	public void setSwitchExpressionRegex(String switchExpressionRegex) {
		this.switchExpressionPattern = FlowMatcherHelper.strigToPattern(switchExpressionRegex);
	}

	public String getStateNameRegex() {
		return FlowStringUtils.safeToString(stateNamePattern);
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNamePattern = FlowMatcherHelper.strigToPattern(stateNameRegex);
	}

	public Class<? extends IFlowState> getType() {
		return type;
	}

	public void setType(Class<? extends IFlowState> type) {
		this.type = type;
	}

	/**
	 * @return the switchExpressionPattern
	 */
	public Pattern getSwitchExpressionPattern() {
		return switchExpressionPattern;
	}


	/**
	 * @param switchExpressionPattern the switchExpressionPattern to set
	 */
	public void setSwitchExpressionPattern(Pattern switchExpressionPattern) {
		this.switchExpressionPattern = switchExpressionPattern;
	}


	/**
	 * @return the stateNamePattern
	 */
	public Pattern getStateNamePattern() {
		return stateNamePattern;
	}


	/**
	 * @param stateNamePattern the stateNamePattern to set
	 */
	public void setStateNamePattern(Pattern stateNamePattern) {
		this.stateNamePattern = stateNamePattern;
	}


	/**
	 * @return the categoryNamePattern
	 */
	public Pattern getCategoryNamePattern() {
		return categoryNamePattern;
	}


	/**
	 * @param categoryNamePattern the categoryNamePattern to set
	 */
	public void setCategoryNamePattern(Pattern categoryNamePattern) {
		this.categoryNamePattern = categoryNamePattern;
	}


	@Override
	protected void finalize() throws Throwable {
		stateNamePattern = null;
		switchExpressionPattern = null;
		categoryNamePattern = null;
		type = null;
		super.finalize();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(": switchExpressionPattern=");
		sb.append(switchExpressionPattern);
		sb.append(", stateNamePattern=");
		sb.append(FlowStringUtils.safeToString(stateNamePattern));
		sb.append(", categoryNamePattern=");
		sb.append(FlowStringUtils.safeToString(categoryNamePattern));
		sb.append(", type=");
		sb.append(type);
		return sb.toString();
	}

}
