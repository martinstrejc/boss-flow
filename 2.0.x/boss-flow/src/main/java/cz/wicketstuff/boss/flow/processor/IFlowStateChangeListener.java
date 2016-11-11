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
package cz.wicketstuff.boss.flow.processor;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

/**
 * Listeners of state changing events.
 * 
 * @author Martin Strejc
 *
 * @param <T> type of flow payload
 */
public interface IFlowStateChangeListener<T extends Serializable> extends Comparable<IPriority>, IPriority {
	
	/**
	 * Listen on entering the state.
	 * See the flow implementation lifecycle to understand 
	 * order of event's orders.
	 * 
	 * @param flow processed flow carter
	 * @param flowState flow to be left
	 * @throws FlowStateListenerException
	 */
	void onStateEntry(IFlowCarter<T> flow, IFlowState flowState) throws FlowStateListenerException;

	/**
	 * Listen on leaving the state.
	 * See the flow implementation lifecycle to understand 
	 * order of event's orders.
	 * 
	 * @param flow processed flow carter
	 * @param flowState flow to be entered
	 * @throws FlowStateListenerException
	 */
	void onStateLeaving(IFlowCarter<T> flow, IFlowState flowState) throws FlowStateListenerException;

}
