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
package cz.wicketstuff.boss.flow.model.basic;

import java.io.Serializable;
import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.IFlowTree;

public class FlowTree implements IFlowTree, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer flowId;
	private String flowName;
	
	private IFlowState defaultInitialState;
	
	private Map<String, IFlowTransition> transitionNamesMap;
	private Map<Integer, IFlowTransition> transitionIdsMap;
	private Map<String, IFlowState> stateNamesMap;
	private Map<Integer, IFlowState> stateIdsMap;
	private Map<IFlowState, IFlowTransition> transitionNextMap;
	private Map<IFlowState, IFlowTransition> transitionPreviousMap;
	

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
		return transitionNamesMap.get(name);
	}

	@Override
	public IFlowTransition getTransition(Integer id) {
		return transitionIdsMap.get(id);
	}

	@Override
	public IFlowTransition getNextTransition(IFlowState state) {
		return transitionNextMap.get(state);
	}

	@Override
	public IFlowTransition getPreviousTransition(IFlowState state) {
		return transitionPreviousMap.get(state);
	}

	@Override
	public IFlowState getState(String name) {
		return stateNamesMap.get(name);
	}

	@Override
	public IFlowState getState(Integer id) {
		return stateIdsMap.get(id);
	}

	@Override
	public boolean containsTransition(String name) {
		return transitionNamesMap.containsKey(name);
	}

	@Override
	public boolean containsTransition(Integer id) {
		return transitionIdsMap.containsKey(id);
	}

	@Override
	public boolean containsState(String name) {
		return stateNamesMap.containsKey(name);
	}

	@Override
	public boolean containsState(Integer id) {
		return stateIdsMap.containsKey(id);
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
	
	/**
	 * @return the transitionNextMap
	 */
	public Map<IFlowState, IFlowTransition> getTransitionNextMap() {
		return transitionNextMap;
	}

	/**
	 * @param transitionNextMap the transitionNextMap to set
	 */
	public void setTransitionNextMap(
			Map<IFlowState, IFlowTransition> transitionNextMap) {
		this.transitionNextMap = transitionNextMap;
	}

	/**
	 * @return the transitionPreviousMap
	 */
	public Map<IFlowState, IFlowTransition> getTransitionPreviousMap() {
		return transitionPreviousMap;
	}

	/**
	 * @param transitionPreviousMap the transitionPreviousMap to set
	 */
	public void setTransitionPreviousMap(
			Map<IFlowState, IFlowTransition> transitionPreviousMap) {
		this.transitionPreviousMap = transitionPreviousMap;
	}

	@Override
	public IFlowState getDefaultInitialState() {
		return defaultInitialState;
	}

	public void setDefaultInitialState(IFlowState defaultInitialState) {
		this.defaultInitialState = defaultInitialState;
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
		defaultInitialState = null;
		super.finalize();
	}

}
