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
package cz.wicketstuff.boss.flow.builder.xml;

import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowState;

/**
 * A carter to hold maps with state and transition holders.
 * 
 * @author Martin Strejc
 *
 */
public class FlowBuilderCarter {

	private Map<Integer, StateCapsule> stateIdsMap;
	private Map<String, StateCapsule> stateNamesMap;
	private Map<Integer, TransitionCapsule> transitionIdsMap;
	private Map<String, TransitionCapsule> transitionNamesMap;

	private IFlowState defaultInitialState;
	
	public FlowBuilderCarter() {
	}

	public FlowBuilderCarter(Map<Integer, StateCapsule> stateIdsMap,
			Map<String, StateCapsule> stateNamesMap,
			Map<Integer, TransitionCapsule> transitionIdsMap,
			Map<String, TransitionCapsule> transitionNamesMap) {
		this.stateIdsMap = stateIdsMap;
		this.stateNamesMap = stateNamesMap;
		this.transitionIdsMap = transitionIdsMap;
		this.transitionNamesMap = transitionNamesMap;
	}

	public Map<Integer, StateCapsule> getStateIdsMap() {
		return stateIdsMap;
	}

	public void setStateIdsMap(Map<Integer, StateCapsule> stateIdsMap) {
		this.stateIdsMap = stateIdsMap;
	}

	public Map<String, StateCapsule> getStateNamesMap() {
		return stateNamesMap;
	}

	public void setStateNamesMap(Map<String, StateCapsule> stateNamesMap) {
		this.stateNamesMap = stateNamesMap;
	}

	public Map<Integer, TransitionCapsule> getTransitionIdsMap() {
		return transitionIdsMap;
	}

	public void setTransitionIdsMap(Map<Integer, TransitionCapsule> transitionIdsMap) {
		this.transitionIdsMap = transitionIdsMap;
	}

	public Map<String, TransitionCapsule> getTransitionNamesMap() {
		return transitionNamesMap;
	}

	public void setTransitionNamesMap(
			Map<String, TransitionCapsule> transitionNamesMap) {
		this.transitionNamesMap = transitionNamesMap;
	}
	
	public TransitionCapsule findTransition(String name) throws IllegalArgumentException {
		if(name == null) {
			throw new IllegalArgumentException("Cannot find transition for id=null and name=null");
		}
		TransitionCapsule tc = null;
		tc = getTransitionNamesMap().get(name);
		return tc;
	}

	public StateCapsule findState(String name) throws IllegalArgumentException {
		if(name == null) {
			throw new IllegalArgumentException("Cannot find transition for id=null and name=null");
		}
		StateCapsule sc = null;
			sc = getStateNamesMap().get(name);
		return sc;
	}

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
