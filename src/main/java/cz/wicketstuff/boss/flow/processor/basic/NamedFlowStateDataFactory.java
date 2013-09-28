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
package cz.wicketstuff.boss.flow.processor.basic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.IFlowStateDataFactory;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;
import cz.wicketstuff.boss.flow.processor.StateDataException;


public class NamedFlowStateDataFactory<T extends Serializable> implements IFlowStateDataFactory<T>, Serializable {

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
	public Serializable createStateData(IFlowCarter<T> flowCarter, IFlowState flowState) throws NoSuchStateException, StateDataException {
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
