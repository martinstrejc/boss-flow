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
package com.etnetera.boss.flow.model.basic;

import java.io.Serializable;

import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.model.IFlowTransition;

public class FlowCarter<T extends Serializable> implements IFlowCarter<T> {

	private static final long serialVersionUID = 1L;
	
	private Long flowProcessId;
	private IFlowTransition previousTransition;
	private IFlowTransition nextTransition;
	private IFlowState previousState;
	private IFlowState currentState;
	private T payload;
	private int stateHit = 0;
	private boolean flowProcessed = false;
	
	public FlowCarter() {
		this(null);
	}

	public FlowCarter(Long flowProcessId) {
		this(flowProcessId, null);
	}

	public FlowCarter(Long flowProcessId, IFlowState currentState) {
		this.flowProcessId = flowProcessId;
		this.currentState = currentState;
	}

	@Override
	public int getStateHit() {
		return stateHit;
	}

	@Override
	public IFlowTransition getPreviousTransition() {
		return previousTransition;
	}

	public void setPreviousTransition(IFlowTransition previousTransition) {
		this.previousTransition = previousTransition;
	}

	@Override
	public IFlowTransition getNextTransition() {
		return nextTransition;
	}

	public void setNextTransition(IFlowTransition nextTransition) {
		this.nextTransition = nextTransition;
	}

	@Override
	public IFlowState getPreviousState() {
		return previousState;
	}

	public void setPreviousState(IFlowState previousState) {
		this.previousState = previousState;
	}

	@Override
	public IFlowState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(IFlowState currentState) {
		this.currentState = currentState;
	}

	@Override
	public T getPayload() {
		return payload;
	}
	
	@Override
	public void setPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public <C extends Serializable> C getStateData() {
		return getCurrentState().getStateData();
	}

	@Override
	public void setStateData(Serializable stateData) {
		getCurrentState().setStateData(stateData);
	}
	
	@Override
	public Long getFlowProcessId() {
		return flowProcessId;
	}

	public void setFlowProcessId(Long flowProcessId) {
		this.flowProcessId = flowProcessId;
	}
	
	@Override
	public boolean isFlowProcessed() {
		return flowProcessed;
	}

	@Override
	public void setFlowProcessed(boolean flowProcessed) {
		this.flowProcessed = flowProcessed;
	}

	@Override
	public void shiftFlow() {
		setPreviousState(getCurrentState());		
		setCurrentState(getNextTransition().getTargetState());
		setPreviousTransition(getNextTransition());
		setNextTransition(null);
		if(getPreviousTransition().isHitCountable()) {
			stateHit++;			
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append("@");
		sb.append(hashCode());		
		sb.append(": {flowProcessId=");
		sb.append(getFlowProcessId());
		sb.append(", stateHit=");
		sb.append(getStateHit());
		sb.append(", previousTransition=");
		sb.append(getPreviousTransition());
		sb.append(", nextTransition=");
		sb.append(getNextTransition());
		sb.append(", previousState=");
		sb.append(getPreviousState());
		sb.append(", currentState=");
		sb.append(getCurrentState());
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append("}");
		return sb.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		currentState = null;
		flowProcessId = null;
		nextTransition = null;
		payload = null;
		previousState = null;
		previousTransition = null;
		super.finalize();
	}

}
