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
import java.util.Iterator;

/**
 * Basic flow state interface.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowState extends Serializable {

	/**
	 * Return the unique numeric identifier of this state.
	 *  
	 * @return the unique numeric identifier
	 */
	public Integer getStateId();
	
	/**
	 * Return the unique string identifier of this state.
	 * 
	 * @return the unique string identifier 
	 */
	public String getStateName();

	/**
	 * Return {@code true} if this is an initial state.
	 * 
	 * @return {@code true} if this is an initial state
	 */
	public boolean isInitialState();

	/**
	 * Return {@code true} if this is an final state.
	 * 
	 * @return {@code true} if this is an final state
	 */
	public boolean isFinalState();

	/**
	 * Return {@code true} if this state can be persisted.
	 * 
	 * @return {@code true} if this state can be persisted
	 */
	public boolean isPersistableState();
		
	/**
	 * Return {@code true} if this state has to be validated.
	 * 
	 * @return {@code true} if this state has to be validated
	 */
	public boolean isStateValidatable();
	
	/**
	 * Return {@code true} if this state require state data
	 * see {@link IFlowCarter#getStateData()}
	 * 
	 * @return {@code true} if this state require state data
	 */
	public boolean isRequireStateData();
	
	/**
	 * Return the state data
	 * see {@link IFlowCarter#getStateData()}
	 * 
	 * @return state data
	 */
	public <C extends Serializable> C getStateData();

	/**
	 * Set state data
	 * see {@link IFlowCarter#setStateData(Serializable)}
	 * 
	 * @param stateData
	 */
	public void setStateData(Serializable stateData);
	
	/**
	 * Return an iterator over all transitions available for this step.
	 * 
	 * @return iterator over all transitions of this step
	 */
	public Iterator<IFlowTransition> getAvailableTransitions();
	
	/**
	 * Return the state ordinal value. Be aware, this value is non-unique and it should be null.
	 * 
	 * @return state ordinal value, it can be null or non-unique
	 */
	public Integer getOrder();
	
}
