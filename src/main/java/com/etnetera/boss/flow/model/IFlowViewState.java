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
package com.etnetera.boss.flow.model;

/**
 * The state interface that represents a view
 * that is displayed to user.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowViewState extends IFlowRealState {

	/**
	 * Return the view name associated to this state.
	 * Example: Page1-Initial-Data.jsp
	 * 
	 * @return view name associated to this state
	 */
	public String getViewName();
	
}
