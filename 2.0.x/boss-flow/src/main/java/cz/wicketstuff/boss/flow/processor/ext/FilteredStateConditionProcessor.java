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
import cz.wicketstuff.boss.flow.processor.condition.IFlowMatchedConditionProcessor;
import cz.wicketstuff.boss.flow.util.FlowMatcherHelper;
import cz.wicketstuff.boss.flow.util.FlowStringUtils;

public abstract class FilteredStateConditionProcessor<T extends Serializable> implements IFlowMatchedConditionProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStateConditionProcessor.class);

	private Pattern conditionExpressionPattern;
	
	private Pattern stateNamePattern;
	
	private Pattern categoryNamePattern;
	
	private Class<? extends IFlowState> type;

	public FilteredStateConditionProcessor() {
	}

	
	public FilteredStateConditionProcessor(String conditionExpressionRegex,
			String stateNameRegex, 
			String categoryNameRegex, 
			Class<? extends IFlowState> type) {
		this(FlowMatcherHelper.strigToPattern(conditionExpressionRegex), 
				FlowMatcherHelper.strigToPattern(stateNameRegex), 
				FlowMatcherHelper.strigToPattern(categoryNameRegex), type);
	}

	public FilteredStateConditionProcessor(Pattern conditionExpressionPattern,
			Pattern stateNamePattern, Pattern categoryNamePattern, 
			Class<? extends IFlowState> type) {
		this.conditionExpressionPattern = conditionExpressionPattern;
		this.categoryNamePattern = categoryNamePattern;
		this.stateNamePattern = stateNamePattern;
		this.type = type;
	}

	@Override
	public boolean match(String conditionExpression, IFlowCarter<T> flow) {
		if(log.isDebugEnabled()) {
			log.debug("match condition: " + conditionExpression + " on " + toString());
		}
		if(log.isTraceEnabled()) {
			log.trace(flow.toString());
		}
		boolean ret = matchExpressionName(conditionExpression) && matchStateName(flow.getCurrentState().getStateName()) && matchType(flow.getCurrentState()); 
		if(log.isDebugEnabled()) {
			log.debug("match result: " + ret);
		}
		return ret;
	}

	public boolean matchExpressionName(String conditionExpression) {
		return this.conditionExpressionPattern == null || conditionExpressionPattern.matcher(conditionExpression).matches();
	}

	public boolean matchStateName(String stateName) {
		return this.stateNamePattern == null || stateNamePattern.matcher(stateName).matches();
	}

	
	public boolean matchType(IFlowState implementingObject) {
		return this.type == IFlowState.class || this.type.isInstance(implementingObject);
	}

	public Class<? extends IFlowState> getType() {
		return type;
	}

	public void setType(Class<? extends IFlowState> type) {
		this.type = type;
	}

	public String getConditionExpressionRegex() {
		return FlowStringUtils.safeToString(conditionExpressionPattern);
	}

	public void setConditionExpressionRegex(String conditionExpressionRegex) {
		this.conditionExpressionPattern = FlowMatcherHelper.strigToPattern(conditionExpressionRegex);
	}

	public String getStateNameRegex() {
		return FlowStringUtils.safeToString(stateNamePattern);
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNamePattern = FlowMatcherHelper.strigToPattern(stateNameRegex);
	}

	/**
	 * @return the conditionExpressionPattern
	 */
	public Pattern getConditionExpressionPattern() {
		return conditionExpressionPattern;
	}


	/**
	 * @param conditionExpressionPattern the conditionExpressionPattern to set
	 */
	public void setConditionExpressionPattern(Pattern conditionExpressionPattern) {
		this.conditionExpressionPattern = conditionExpressionPattern;
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
		conditionExpressionPattern = null;
		categoryNamePattern = null;
		stateNamePattern = null;
		type = null;
		super.finalize();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(": conditionExpressionPattern=");
		sb.append(conditionExpressionPattern);
		sb.append(", stateNamePattern=");
		sb.append(FlowStringUtils.safeToString(stateNamePattern));
		sb.append(", categoryNamePattern=");
		sb.append(FlowStringUtils.safeToString(categoryNamePattern));
		sb.append(", type=");
		sb.append(type);
		return sb.toString();
	}

}
