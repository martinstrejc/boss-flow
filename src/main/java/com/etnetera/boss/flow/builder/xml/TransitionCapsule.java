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
package com.etnetera.boss.flow.builder.xml;

import com.etnetera.boss.flow.builder.xml.jaxb.TransitionType;
import com.etnetera.boss.flow.model.basic.FlowTransition;

/**
 * A capsule of a transition descriptor containing transition object
 * and its XML descriptor.
 * 
 * @author Martin Strejc
 *
 */
public class TransitionCapsule {

	private TransitionType transition;
	private FlowTransition flowTransition;
	
	public TransitionCapsule() {
	}

	public TransitionCapsule(TransitionType transition) {
		this.transition = transition;
	}

	public TransitionCapsule(FlowTransition flowTransition) {
		this.flowTransition = flowTransition;
	}

	public TransitionCapsule(TransitionType transition,
			FlowTransition flowTransition) {
		this.transition = transition;
		this.flowTransition = flowTransition;
	}

	public TransitionType getTransition() {
		return transition;
	}

	public void setTransition(TransitionType transition) {
		this.transition = transition;
	}

	public FlowTransition getFlowTransition() {
		return flowTransition;
	}

	public void setFlowTransition(FlowTransition flowTransition) {
		this.flowTransition = flowTransition;
	}

	@Override
	protected void finalize() throws Throwable {
		transition = null;
		flowTransition = null;
		super.finalize();
	}
	
}
