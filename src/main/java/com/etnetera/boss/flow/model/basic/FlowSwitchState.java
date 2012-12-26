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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.etnetera.boss.flow.model.IFlowSwitchState;
import com.etnetera.boss.flow.model.IFlowTransition;
import com.etnetera.boss.flow.model.ISwitchCase;

public class FlowSwitchState extends FlowVirtualState implements IFlowSwitchState {

	private static final long serialVersionUID = 1L;

	private String switchExpression;
	private IFlowTransition defaultTransition;
	private List<ISwitchCase> switchCasesList;
	
	public FlowSwitchState() {
		super();
	}

	public FlowSwitchState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}

	public FlowSwitchState(String stateName) {
		super(stateName);
	}

	public FlowSwitchState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowSwitchState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}

	@Override
	public String getSwitchExpression() {
		return switchExpression;
	}

	@Override
	public Iterator<ISwitchCase> getSwitchCases() {
		return getSwitchCasesList().iterator();
	}

	@Override
	public IFlowTransition getDefaultTransition() {
		return defaultTransition;
	}

	public List<ISwitchCase> getSwitchCasesList() {
		return switchCasesList;
	}

	public void setSwitchCasesList(List<ISwitchCase> switchCasesList) {
		if(switchCasesList instanceof Serializable) {
			this.switchCasesList = switchCasesList;			
		} else {
			this.switchCasesList = new ArrayList<>(switchCasesList);
		}
	}

	public void setSwitchExpression(String switchExpression) {
		this.switchExpression = switchExpression;
	}

	public void setDefaultTransition(IFlowTransition defaultTransition) {
		this.defaultTransition = defaultTransition;
	}
	
	@Override
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		transitionsMap.put(getDefaultTransition().getTransitionName(), getDefaultTransition());
		for(ISwitchCase sc : getSwitchCasesList()) {
			transitionsMap.put(sc.getTransition().getTransitionName(), sc.getTransition());
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if(switchCasesList != null) {
			switchCasesList.clear();
			switchCasesList = null;
		}
		defaultTransition = null;
		switchExpression = null;
		super.finalize();
	}

}
