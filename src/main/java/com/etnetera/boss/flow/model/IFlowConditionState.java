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
 * The condition state is an equivalent to 'if' in java language.
 * When the expression is {@code true}, then the 'thenTransition' is going to be processed,
 * otherwise the 'elseTransition' is going to be processed.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowConditionState extends IFlowVirtualState {

	/**
	 * Return the expression name that the processor try
	 * to find and it is used to decide which transition will be processed.
	 * 
	 * @return expression name
	 */
	public String getConditionExpression();
	
	/**
	 * Return the transition that will be processed when condition is {@code true}
	 * 
	 * @return transition processed if condition is {@code true}
	 */
	public IFlowTransition getThenTransition();
	
	/**
	 * Return the transition that will be processed when condition is 'false'
	 * 
	 * @return transition processed if condition is {@code false}
	 */
	public IFlowTransition getElseTransition();
	
}
