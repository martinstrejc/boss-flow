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

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionResolver;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;

public class SimpleFlowTransitionResolver<T extends Serializable> implements IFlowTransitionResolver<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private IFlowTree flowTree;
	
	public SimpleFlowTransitionResolver() {
		this(null);
	}
	
	public SimpleFlowTransitionResolver(IFlowTree flowTree) {
		this.flowTree = flowTree;
	}

	@Override
	public IFlowTransition resolveTransition(IFlowCarter<T> flow, String transitionName)
			throws NoSuchTransitionException {
		IFlowTransition t = getFlowTree().getTransition(transitionName);
		if(t == null) {
			throw new NoSuchTransitionException("Transition name='" + transitionName + "' does not exist.");
		}
		return t;
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
