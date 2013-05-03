/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.wicketstuff.boss.flow.model.basic;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;

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

	/**
	 * The setter of stateHit is present due to persisting flow.
	 * <br>
	 * Do not set it manually outside the {@link FlowCarter} rather use to {@link #shiftFlow()}
	 * 
	 * @param stateHit the stateHit to set
	 */
	public void setStateHit(int stateHit) {
		this.stateHit = stateHit;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currentState == null) ? 0 : currentState.hashCode());
		result = prime * result
				+ ((flowProcessId == null) ? 0 : flowProcessId.hashCode());
		result = prime * result + stateHit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowCarter<?> other = (FlowCarter<?>) obj;
		if (currentState == null) {
			if (other.currentState != null)
				return false;
		} else if (!currentState.equals(other.currentState))
			return false;
		if (flowProcessId == null) {
			if (other.flowProcessId != null)
				return false;
		} else if (!flowProcessId.equals(other.flowProcessId))
			return false;
		if (stateHit != other.stateHit)
			return false;
		return true;
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
