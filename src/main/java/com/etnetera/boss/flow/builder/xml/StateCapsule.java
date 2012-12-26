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

import com.etnetera.boss.flow.builder.xml.jaxb.StateType;
import com.etnetera.boss.flow.model.basic.FlowState;

/**
 * A capsule of a state descriptor containing state object
 * and its XML descriptor.
 * 
 * @author Martin Strejc
 *
 */
public class StateCapsule {
	
	private StateType state;
	private FlowState flowState;

	public StateCapsule() {
	}
	
	public StateCapsule(StateType state) {
		this.state = state;
	}
	
	public StateCapsule(FlowState flowState) {
		this.flowState = flowState;
	}
	
	public StateCapsule(StateType state, FlowState flowState) {
		this.state = state;
		this.flowState = flowState;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public FlowState getFlowState() {
		return flowState;
	}

	public void setFlowState(FlowState flowState) {
		this.flowState = flowState;
	}

	@Override
	protected void finalize() throws Throwable {
		state = null;
		flowState = null;
		super.finalize();
	}
	
}
