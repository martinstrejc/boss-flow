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
import java.util.Map;

import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.model.IFlowTransition;
import com.etnetera.boss.flow.model.IFlowTree;

public class FlowTree implements IFlowTree, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer flowId;
	private String flowName;
	
	private Map<String, IFlowTransition> transitionNamesMap;
	private Map<Integer, IFlowTransition> transitionIdsMap;
	private Map<String, IFlowState> stateNamesMap;
	private Map<Integer, IFlowState> stateIdsMap;
	

	public FlowTree() {
	}
	
	public FlowTree(Integer flowId, String flowName) {
		this.flowId = flowId;
		this.flowName = flowName;
	}

	@Override
	public Integer getFlowId() {
		return flowId;
	}

	@Override
	public String getFlowName() {
		return flowName;
	}

	@Override
	public IFlowTransition getTransition(String name) {
		return getTransitionNamesMap().get(name);
	}

	@Override
	public IFlowTransition getTransition(Integer id) {
		return getTransitionIdsMap().get(id);
	}

	@Override
	public IFlowState getState(String name) {
		return getStateNamesMap().get(name);
	}

	@Override
	public IFlowState getState(Integer id) {
		return getStateIdsMap().get(id);
	}

	@Override
	public boolean containsTransition(String name) {
		return getTransitionNamesMap().containsKey(name);
	}

	@Override
	public boolean containsTransition(Integer id) {
		return getTransitionIdsMap().containsKey(id);
	}

	@Override
	public boolean containsState(String name) {
		return getStateNamesMap().containsKey(name);
	}

	@Override
	public boolean containsState(Integer id) {
		return getStateIdsMap().containsKey(id);
	}

	public Map<String, IFlowTransition> getTransitionNamesMap() {
		return transitionNamesMap;
	}

	public void setTransitionNamesMap(
			Map<String, IFlowTransition> transitionNamesMap) {
		this.transitionNamesMap = transitionNamesMap;
	}

	public Map<Integer, IFlowTransition> getTransitionIdsMap() {
		return transitionIdsMap;
	}

	public void setTransitionIdsMap(Map<Integer, IFlowTransition> transitionIdsMap) {
		this.transitionIdsMap = transitionIdsMap;
	}

	public Map<String, IFlowState> getStateNamesMap() {
		return stateNamesMap;
	}

	public void setStateNamesMap(Map<String, IFlowState> stateNamesMap) {
		this.stateNamesMap = stateNamesMap;
	}

	public Map<Integer, IFlowState> getStateIdsMap() {
		return stateIdsMap;
	}

	public void setStateIdsMap(Map<Integer, IFlowState> stateIdsMap) {
		this.stateIdsMap = stateIdsMap;
	}

	@Override
	protected void finalize() throws Throwable {
		if(stateIdsMap != null) {
			stateIdsMap.clear();
			stateIdsMap = null;
		}
		if(stateNamesMap != null) {
			stateNamesMap.clear();
			stateNamesMap = null;
		}
		if(transitionIdsMap != null) {
			transitionIdsMap.clear();
			transitionIdsMap = null;
		}
		if(transitionNamesMap != null) {
			transitionNamesMap.clear();
			transitionNamesMap = null;
		}
		super.finalize();
	}

}
