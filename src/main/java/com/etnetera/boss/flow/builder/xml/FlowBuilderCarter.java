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
package com.etnetera.boss.flow.builder.xml;

import java.util.Map;

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
