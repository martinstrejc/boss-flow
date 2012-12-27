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

/**
 * Interface of transition defines just its abstract methods.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowTransition extends Serializable {

	/**
	 * Return the unique numeric identifier of this transition.
	 * 
	 * @return unique numeric identifier 
	 */
	public Integer getTransitionId();

	/**
	 * Return the unique string identifier of this transition.
	 * 
	 * @return unique string identifier
	 */
	public String getTransitionName();
	
	/**
	 * Return the state that this transition shit flow to.
	 * 
	 * @return target state to shift flow
	 */
	public IFlowState getTargetState();
	
	/**
	 * Return {@code true} if state hit of flow can be counted
	 * when this transition is finished.
	 * 
	 * @return {@code true} if state hit of flow can be counted
	 */
	public boolean isHitCountable();

}
