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
package cz.wicketstuff.boss.flow;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.processor.IFlowProcessor;

/**
 * Flow processor factory interface. The processor is responsible of correct flow processing.
 * The factory just can create or get important processing classes.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowProcessorFactory {

	/**
	 * Return the default flow processor
	 * 
	 * @return flow processor
	 */
	public <T extends Serializable> IFlowProcessor<T> getFlowProcessor();

	/**
	 * Return the flow processor associated to the specified flow identified by its name.
	 * 
	 * @param flowName
	 * @return flow processor
	 */
	public <T extends Serializable> IFlowProcessor<T> getFlowProcessor(String flowName);
	
}
