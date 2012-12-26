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
import com.etnetera.boss.flow.processor.condition.IFlowMatchedSwitchProcessor;

public abstract class FilteredStateSwitchProcessor<T extends Serializable> implements IFlowMatchedSwitchProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStateSwitchProcessor.class);

	private String switchExpressionRegex;
	
	private String stateNameRegex;
	
	private Class<? extends IFlowState> type;

	public FilteredStateSwitchProcessor() {
	}

	
	public FilteredStateSwitchProcessor(String switchExpressionRegex,
			String stateNameRegex, Class<? extends IFlowState> type) {
		this.switchExpressionRegex = switchExpressionRegex;
		this.stateNameRegex = stateNameRegex;
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
		return this.switchExpressionRegex == null || switchExpression.matches(this.switchExpressionRegex);
	}

	public boolean matchStateName(String stateName) {
		return this.stateNameRegex == null || stateName.matches(this.stateNameRegex);
	}

	
	public boolean matchType(IFlowState implementingObject) {
		return this.type == IFlowState.class || this.type.isInstance(implementingObject);
	}

	public String getSwitchExpressionRegex() {
		return switchExpressionRegex;
	}

	public void setSwitchExpressionRegex(String switchExpressionRegex) {
		this.switchExpressionRegex = switchExpressionRegex;
	}

	public String getStateNameRegex() {
		return stateNameRegex;
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNameRegex = stateNameRegex;
	}

	public Class<? extends IFlowState> getType() {
		return type;
	}

	public void setType(Class<? extends IFlowState> type) {
		this.type = type;
	}

	@Override
	protected void finalize() throws Throwable {
		stateNameRegex = null;
		switchExpressionRegex = null;
		type = null;
		super.finalize();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(": switchExpressionRegex=");
		sb.append(switchExpressionRegex);
		sb.append(", stateNameRegex=");
		sb.append(stateNameRegex);
		sb.append(", type=");
		sb.append(type);
		return sb.toString();
	}

}
