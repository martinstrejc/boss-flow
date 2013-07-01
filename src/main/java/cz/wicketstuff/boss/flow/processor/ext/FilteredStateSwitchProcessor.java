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

public abstract class FilteredStateSwitchProcessor<T extends Serializable> implements IFlowMatchedSwitchProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStateSwitchProcessor.class);

	private Pattern switchExpressionPattern;
	
	private Pattern stateNamePattern;
	
	private Class<? extends IFlowState> type;

	public FilteredStateSwitchProcessor() {
	}

	
	public FilteredStateSwitchProcessor(String switchExpressionRegex,
			String stateNameRegex, Class<? extends IFlowState> type) {
		this.switchExpressionPattern = Pattern.compile(switchExpressionRegex);
		this.stateNamePattern = Pattern.compile(stateNameRegex);
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
		return switchExpressionPattern.toString();
	}

	public void setSwitchExpressionRegex(String switchExpressionRegex) {
		this.switchExpressionPattern = Pattern.compile(switchExpressionRegex);
	}

	public String getStateNameRegex() {
		return stateNamePattern.toString();
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNamePattern = Pattern.compile(stateNameRegex);
	}

	public Class<? extends IFlowState> getType() {
		return type;
	}

	public void setType(Class<? extends IFlowState> type) {
		this.type = type;
	}

	@Override
	protected void finalize() throws Throwable {
		stateNamePattern = null;
		switchExpressionPattern = null;
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
		sb.append(stateNamePattern.toString());
		sb.append(", type=");
		sb.append(type);
		return sb.toString();
	}

}
