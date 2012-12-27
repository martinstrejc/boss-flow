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
 * The interface defines a dummy state that just pass trough
 * using the next transition to redirect to another state.
 * 
 * Transition can also reference the target state directly,
 * however it is sometimes useful to have an state beside two states
 * because of event listeners or if a refactoring of flow is expected.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowJoinState extends IFlowVirtualState {
	
	/**
	 * Return the transition that is going to be processed.
	 * 
	 * @return transition to process
	 */
	public IFlowTransition getNextTransition();

}
