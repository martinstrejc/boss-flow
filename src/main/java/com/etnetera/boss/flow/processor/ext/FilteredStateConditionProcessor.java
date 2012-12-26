/*
 * Et netera, http://boss.etnetera.cz - Copyright (C) 2012 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 */
package com.etnetera.boss.flow.processor.ext;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.processor.condition.IFlowMatchedConditionProcessor;

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
