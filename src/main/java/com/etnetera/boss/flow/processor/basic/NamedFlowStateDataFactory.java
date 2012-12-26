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
package com.etnetera.boss.flow.processor.basic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.processor.IFlowStateDataFactory;
import com.etnetera.boss.flow.processor.NoSuchStateException;
import com.etnetera.boss.flow.processor.StateDataException;


public class NamedFlowStateDataFactory implements IFlowStateDataFactory, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Class<Serializable>> stateDataMap;	
	private boolean exceptionIfStateNotExist;
	
	public NamedFlowStateDataFactory() {
		this(null);
	}

	public NamedFlowStateDataFactory(
			Map<String, Class<Serializable>> stateDataMap) {
		this(stateDataMap, false);
	}

	public NamedFlowStateDataFactory(
			Map<String, Class<Serializable>> stateDataMap,
			boolean exceptionIfStateNotExist) {
		this.stateDataMap = stateDataMap;
		this.exceptionIfStateNotExist = exceptionIfStateNotExist;
	}

	@Override
	public Serializable createStateData(IFlowState flowState) throws NoSuchStateException, StateDataException {
		Class<Serializable> c = getStateDataMap().get(flowState.getStateName());
		if(c == null) {
			if(exceptionIfStateNotExist) {
				throw new NoSuchStateException("Cannot find state '" + flowState.getStateName() + "'");
			}
			return null;
		}		
		try {
			return c.newInstance();
		} catch (InstantiationException e) {
			throw new StateDataException("Cannot instantiate class for '" + c.getName() + "' for state '" + flowState.getStateName() + "', probably the constructor thrown an exception", e);
		} catch (IllegalAccessException e) {
			throw new StateDataException("Cannot instantiate class for '" + c.getName() + "' for state '" + flowState.getStateName() + "', probably constructor is private or an interface is mapped instead a regular class.", e);
		}
	}

	public boolean isExceptionIfStateNotExist() {
		return exceptionIfStateNotExist;
	}

	public void setExceptionIfStateNotExist(boolean exceptionIfStateNotExist) {
		this.exceptionIfStateNotExist = exceptionIfStateNotExist;
	}

	public Map<String, Class<Serializable>> getStateDataMap() {
		if(stateDataMap == null) {
			stateDataMap = new HashMap<String, Class<Serializable>>();
		}
		return stateDataMap;
	}

	public void setStateDataMap(Map<String, Class<Serializable>> stateDataMap) {
		this.stateDataMap = stateDataMap;
	}

	@Override
	protected void finalize() throws Throwable {
		if(stateDataMap != null) {
			stateDataMap.clear();
			stateDataMap = null;
		}
		super.finalize();
	}

}
