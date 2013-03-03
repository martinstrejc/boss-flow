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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.condition.IFlowMatchedConditionProcessor;

public abstract class FilteredStateConditionProcessor<T extends Serializable> implements IFlowMatchedConditionProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStateConditionProcessor.class);

	private String conditionExpressionRegex;
	
	private String stateNameRegex;
	
	private Class<? extends IFlowState> type;

	public FilteredStateConditionProcessor() {
	}

	
	public FilteredStateConditionProcessor(String conditionExpressionRegex,
			String stateNameRegex, Class<? extends IFlowState> type) {
		this.conditionExpressionRegex = conditionExpressionRegex;
		this.stateNameRegex = stateNameRegex;
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
		return this.conditionExpressionRegex == null || conditionExpression.matches(this.conditionExpressionRegex);
	}

	public boolean matchStateName(String stateName) {
		return this.stateNameRegex == null || stateName.matches(this.stateNameRegex);
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
		return conditionExpressionRegex;
	}

	public void setConditionExpressionRegex(String conditionExpressionRegex) {
		this.conditionExpressionRegex = conditionExpressionRegex;
	}

	public String getStateNameRegex() {
		return stateNameRegex;
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNameRegex = stateNameRegex;
	}

	@Override
	protected void finalize() throws Throwable {
		conditionExpressionRegex = null;
		stateNameRegex = null;
		type = null;
		super.finalize();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(": conditionExpressionRegex=");
		sb.append(conditionExpressionRegex);
		sb.append(", stateNameRegex=");
		sb.append(stateNameRegex);
		sb.append(", type=");
		sb.append(type);
		return sb.toString();
	}

}
