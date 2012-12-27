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
 * The interface of a case used in {@link IFlowSwitchState}
 * 
 * @author Martin Strejc
 *
 */
public interface ISwitchCase {

	/**
	 * Return the name of this case that is used to compare.
	 * 
	 * @return case name
	 */
	public String getCaseName();
	
	/**
	 * Return the transition that is going to be processed if the name matches.
	 * 
	 * @return transition to process when name matches
	 */
	public IFlowTransition getTransition();

}
