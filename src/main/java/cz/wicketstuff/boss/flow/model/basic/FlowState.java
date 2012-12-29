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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;

public class FlowState implements IFlowState {

	private static final long serialVersionUID = 1L;

	private Integer stateId;
	private String stateName;
	private Map<String, IFlowTransition> transitionsMap;
	private boolean finalState = false;
	private boolean initialState = false;
	private boolean requireStateData;
	private boolean stateValidatable;
	private boolean persistableState;
	private Serializable stateData;

	public FlowState() {
		this(null, null);
	}

	public FlowState(String stateName) {
		this(null, stateName, new HashMap<String, IFlowTransition>());
	}

	public FlowState(Integer stateId, String stateName) {
		this(stateId, stateName, new HashMap<String, IFlowTransition>());
	}

	public FlowState(Integer stateId, String stateName, Map<String, IFlowTransition> transitionsMap) {
		this.stateName = stateName;
		this.stateId = stateId;
		this.transitionsMap = transitionsMap;
	}
	
	public void putFlowTransition(IFlowTransition transition) {
		getTransitionsMap().put(transition.getTransitionName(), transition);
	}

	@Override
	public String getStateName() {
		return stateName;
	}

	@Override
	public boolean isFinalState() {
		return finalState;
	}

	@Override
	public boolean isInitialState() {
		return initialState;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}

	public void setInitialState(boolean startState) {
		this.initialState = startState;
	}

	@Override
	public boolean isPersistableState() {
		return persistableState;
	}

	public void setPersistableState(boolean persistableState) {
		this.persistableState = persistableState;
	}

	@Override
	public Integer getStateId() {
		return stateId;
	}

	@Override
	public boolean isStateValidatable() {
		return stateValidatable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <C extends Serializable> C getStateData() {
		return (C) stateData;
	}

	@Override
	public void setStateData(Serializable stateData) {
		this.stateData = stateData;
	}

	@Override
	public boolean isRequireStateData() {
		return requireStateData;
	}

	@Override
	public Iterator<IFlowTransition> getAvailableTransitions() {
		return getTransitionsMap().values().iterator();
	}

	public void setTransitionsMap(Map<String, IFlowTransition> transitionsMap) {
		this.transitionsMap = transitionsMap;		
	}

	public Map<String, IFlowTransition> getTransitionsMap() {
		if(transitionsMap == null) {
			transitionsMap = createTransitionMap();
			rebuildTransitionMap(transitionsMap);
		}
		return transitionsMap;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public void setStateValidatable(boolean stateValidatable) {
		this.stateValidatable = stateValidatable;
	}

	public void setRequireStateData(boolean requireStateData) {
		this.requireStateData = requireStateData;
	}

	public Map<String, IFlowTransition> createTransitionMap() {
		return new HashMap<String, IFlowTransition>();
	}

	@Override
	public String toString() {
		return super.toString() + " is [id:" + getStateId() + ", name:" + getStateName() + "]";
	}

	public void rebuildTransitionMap() {
		if(transitionsMap == null) {
			transitionsMap = createTransitionMap();
		}	
		rebuildTransitionMap(transitionsMap);
	}
	
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		
	}

	@Override
	protected void finalize() throws Throwable {
		if(transitionsMap != null) {
			transitionsMap.clear();
			transitionsMap = null;
		}
		stateData = null;
		super.finalize();
	}

}
