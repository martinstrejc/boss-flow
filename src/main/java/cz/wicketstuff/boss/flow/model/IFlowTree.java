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
