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
package cz.wicketstuff.boss.flow.model.basic;

import java.util.HashMap;
import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowConditionState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;

public class FlowConditionState extends FlowVirtualState implements IFlowConditionState {

	private static final long serialVersionUID = 1L;

	private String conditionExpression;
	private IFlowTransition thenTransition;
	private IFlowTransition elseTransition;
	
	public FlowConditionState() {
		this(null);
	}

	public FlowConditionState(String stateName) {
		super(stateName, new HashMap<String, IFlowTransition>());
	}
	
	public FlowConditionState(String conditionExpression,
			IFlowTransition thenTransition, IFlowTransition elseTransition) {
		super(null, new HashMap<String, IFlowTransition>());
		this.conditionExpression = conditionExpression;
		this.thenTransition = thenTransition;
		this.elseTransition = elseTransition;
		rebuildTransitionMap();
	}

	public FlowConditionState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowConditionState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}

	public FlowConditionState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}

	@Override
	public String getConditionExpression() {
		return conditionExpression;
	}

	@Override
	public IFlowTransition getThenTransition() {
		return thenTransition;
	}

	@Override
	public IFlowTransition getElseTransition() {
		return elseTransition;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	public void setThenTransition(IFlowTransition thenTransition) {
		this.thenTransition = thenTransition;
		rebuildTransitionMap();
	}

	public void setElseTransition(IFlowTransition elseTransition) {
		this.elseTransition = elseTransition;
		rebuildTransitionMap();
	}
	
	@Override
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		transitionsMap.clear();
		if(getThenTransition() != null) {
			transitionsMap.put(getThenTransition().getTransitionName(), getThenTransition());			
		}
		if(getElseTransition() != null) {
			transitionsMap.put(getElseTransition().getTransitionName(), getElseTransition());
		}
	}
		
	@Override
	protected void finalize() throws Throwable {
		conditionExpression = null;
		elseTransition = null;
		thenTransition = null;
		super.finalize();
	}

}
