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

import com.etnetera.boss.flow.model.IFlowTransition;
import com.etnetera.boss.flow.model.IFlowViewState;

public class FlowViewState extends FlowRealState implements IFlowViewState {

	private static final long serialVersionUID = 1L;

	private String viewName;
	
	public FlowViewState() {
		this(null);
	}

	public FlowViewState(String stateName) {
		super(stateName);
	}
	

	public FlowViewState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}

	public FlowViewState(String stateName,
			Map<String, IFlowTransition> transitionsMap, String viewName) {
		super(stateName, transitionsMap);
		this.viewName = viewName;
	}

	public FlowViewState(String stateName, String viewName) {
		this(stateName, null, viewName);
	}

	@Override
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public FlowViewState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowViewState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}
	
	
	
}
