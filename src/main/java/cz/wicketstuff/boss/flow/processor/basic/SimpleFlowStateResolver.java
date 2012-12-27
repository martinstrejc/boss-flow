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
package cz.wicketstuff.boss.flow.processor.basic;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.processor.IFlowStateResolver;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;

public class SimpleFlowStateResolver implements IFlowStateResolver, Serializable {

	private static final long serialVersionUID = 1L;
	
	private IFlowTree flowTree;

	public SimpleFlowStateResolver() {
		this(null);
	}
	
	public SimpleFlowStateResolver(IFlowTree flowTree) {
		super();
		this.flowTree = flowTree;
	}

	@Override
	public IFlowState resolveState(String stateName)
			throws NoSuchStateException {
		IFlowState s = getFlowTree().getState(stateName);
		if(s == null) {
			throw new NoSuchStateException("State name='" + stateName + "' does not exist.");
		}
		return s;
	}

	public IFlowTree getFlowTree() {
		return flowTree;
	}

	public void setFlowTree(IFlowTree flowTree) {
		this.flowTree = flowTree;
	}

	@Override
	protected void finalize() throws Throwable {
		flowTree = null;
		super.finalize();
	}


}
