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
package com.etnetera.boss.flow.model.basic;

import java.util.Map;

import com.etnetera.boss.flow.model.IFlowJoinState;
import com.etnetera.boss.flow.model.IFlowTransition;

public class FlowJoinState extends FlowVirtualState implements IFlowJoinState {

	private static final long serialVersionUID = 1L;

	private IFlowTransition nextTransition;
	
	public FlowJoinState() {
		super();
	}

	public FlowJoinState(String stateName,
			IFlowTransition nextTransition) {
		super(stateName, null);
		setNextTransition(nextTransition);
	}

	public FlowJoinState(String stateName) {
		super(stateName);
	}
	
	public FlowJoinState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}
	

	public FlowJoinState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowJoinState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}

	@Override
	public IFlowTransition getNextTransition() {
		return nextTransition;
	}

	public void setNextTransition(IFlowTransition nextTransition) {
		this.nextTransition = nextTransition;
	}

	@Override
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		transitionsMap.clear();
		transitionsMap.put(nextTransition.getTransitionName(), nextTransition);
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		nextTransition = null;
		super.finalize();
	}

	
	
}
