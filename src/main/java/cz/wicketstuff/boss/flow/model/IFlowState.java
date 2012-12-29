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
	
}
