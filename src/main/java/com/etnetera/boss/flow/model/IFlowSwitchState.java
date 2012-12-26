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

import java.util.Iterator;

/**
 * The switch state is an equivalent to 'switch' in java language.
 * When the result of expression equals one of {@link #getSwitchCases()} 
 * the associated transition is going to be processed,
 * otherwise nothing match 'defaultTransition' is going to be processed.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowSwitchState extends IFlowVirtualState {
	
	/**
	 * Return the expression name that the processor try
	 * to find and it is used to decide which transition will be processed.
	 * 
	 * @return expression name
	 */
	public String getSwitchExpression();

	/**
	 * Return iterator over possible switch cases.
	 * 
	 * @return switch cases iterator
	 */
	public Iterator<ISwitchCase> getSwitchCases();
	
	/**
	 * Return the default transition used when none of {@link #getSwitchCases()}
	 * matches the result of the expression.
	 * 
	 * @return default transition
	 */
	public IFlowTransition getDefaultTransition();
	
}
