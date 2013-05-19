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
package cz.wicketstuff.boss.flow.model;

import java.io.Serializable;

/**
 * Basic flow carter interface that holds all important data.
 * 
 * @author Martin Strejc
 *
 * @param <T> type of flow payload
 */
public interface IFlowCarter<T extends Serializable> extends Serializable {
	
	/**
	 * Return the unique identifier of this flow, it is usually set once when flow is starting.
	 * 
	 * @return unique identifier of this flow
	 * 
	 */
	public Long getFlowProcessId();
	
	/**
	 * Return the number of hits of states.
	 * The implementation is very important
	 * to change this number when state is changed
	 * to notify a flow processor about state change.
	 * 
	 * @return return the state hit/counter
	 */
	public int getStateHit();
	
	/**
	 * Return the previous transition that has been shifting the flow to the current state.
	 * It is null, when the flow is just in an initial state.
	 * 
	 * @return the previous transition or null
	 */
	public IFlowTransition getPreviousTransition();

	/**
	 * Return the next transition that is going to be processed.
	 * It is null, when no transition is going to be processed
	 * or when the flow is in a final state.
	 * 
	 * @return the next transition or null
	 */
	public IFlowTransition getNextTransition();

	/**
	 * Return the previous state or null if the current state is the initial state.
	 * 
	 * @return the previous state or null
	 */
	public IFlowState getPreviousState();

	/**
	 * Return the current state. There is no difference if it is initial state,
	 * common state or final state. The current state cannot be null.
	 * 
	 * @return the current state, never null
	 */
	public IFlowState getCurrentState();

	/**
	 * Set the next transition that is going to be processed. 
	 * Just setting the next transition is recommended
	 * to consist the flow process and security.
	 * 
	 * @param nextTransition
	 */
	public void setNextTransition(IFlowTransition nextTransition);
	
	/**
	 * Shift flow from the current state to the next state
	 * and increase the stateHit by one.
	 */
	public void shiftFlow();
	
	/**
	 * Return state data that can be an object.
	 * Each state can associate its data
	 * and the flow carter can hold that data.
	 * 
	 * @return state data, null is allowed
	 */
	public <C extends Serializable> C getStateData();

	/**
	 * Set state data associated with a state.
	 * 
	 * @param stateData
	 */
	public void setStateData(Serializable stateData);
	
	/**
	 * Return the model object or data of the whole flow.
	 * For example: the contract data (name, number, date, text, subscriber, etc.)
	 * in a contract flow. 
	 * 
	 * @return the flow model object
	 */
	public T getPayload();

	/**
	 * Set the flow model object, see {@link #getPayload()}
	 * 
	 * @param payload
	 */
	public void setPayload(T payload);
	
	/**
	 * Return {@code true} if flow is processed right now.
	 * 
	 * @return {@code true} if flow is processed 
	 */
	public boolean isFlowProcessed();
	
	/**
	 * Set {@code true} to mark the flow as being processed
	 * 
	 * @param flowProcessed {@code true} to mark the flow as being processed
	 */
	public void setFlowProcessed(boolean flowProcessed);

}
