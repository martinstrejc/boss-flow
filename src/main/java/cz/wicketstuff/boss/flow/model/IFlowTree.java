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

/**
 * The basic flow data structure descriptor.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowTree {

	/**
	 * Return the unique number identifier of this flow
	 * 
	 * @return unique number identifier 
	 */
	public Integer getFlowId();
	
	/**
	 * Return the unique string identifier of this flow
	 * 
	 * @return unique string identifier 
	 */
	public String getFlowName();
	
	/**
	 * Find a transition by name or return null if transition hasn't been found.
	 * 
	 * @param name
	 * @return transition or null
	 */
	public IFlowTransition getTransition(String name);

	/**
	 * Find the next transition of a given state or return null if transition hasn't been found.
	 * 
	 * @param name
	 * @return transition or null
	 */
	public IFlowTransition getNextTransition(IFlowState state);

	/**
	 * Find the previous transition of a given state or return null if transition hasn't been found.
	 * 
	 * @param name
	 * @return transition or null
	 */
	public IFlowTransition getPreviousTransition(IFlowState state);
	
	/**
	 * Find a transition by id or return null if transition hasn't been found.
	 * 
	 * @param id
	 * @return transition or null
	 */
	public IFlowTransition getTransition(Integer id);
	
	/**
	 * Find a state by id or return null if state hasn't been found.
	 * 
	 * @param name
	 * @return state or null
	 */
	public IFlowState getState(String name);

	/**
	 * Find a first initial state or set default initial state,
	 * depends on implementation. Never returns null!
	 * 
	 * @return default initial state, never null
	 */
	public IFlowState getDefaultInitialState();
	
	/**
	 * Find a state by id or return null if state hasn't been found.
	 * 
	 * @param id
	 * @return state or null
	 */
	public IFlowState getState(Integer id);
	
	/**
	 * Check if a transition exists at the name.
	 * 
	 * @param name
	 * @return {@code true} if exists
	 */
	public boolean containsTransition(String name);
	
	/**
	 * Check if a transition exists at the id.
	 * 
	 * @param id
	 * @return {@code true} if exists
	 */
	public boolean containsTransition(Integer id);

	/**
	 * Check if a state exists at the name.
	 * 
	 * @param name
	 * @return {@code true} if exists
	 */
	public boolean containsState(String name);
	
	/**
	 * Check if a state exists at the id.
	 * 
	 * @param id
	 * @return {@code true} if exists
	 */
	public boolean containsState(Integer id);
	
}
