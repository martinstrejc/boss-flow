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

import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.model.IFlowTransition;

public class FlowTransition implements IFlowTransition {

	private static final long serialVersionUID = 1L;

	private Integer transitionId;
	private String transitionName;
	private IFlowState targetState;
	private boolean hitCountable = true;
	
	public FlowTransition() {
		this(null, null);
	}

	public FlowTransition(Integer transitionId, String transitionName) {
		this(transitionId, transitionName, null);
	}

	public FlowTransition(Integer transitionId, String transitionName, IFlowState targetState) {
		this.transitionName = transitionName;
		this.transitionId = transitionId;
		this.targetState = targetState;
		
	}

	@Override
	public String getTransitionName() {
		return transitionName;
	}

	@Override
	public IFlowState getTargetState() {
		return targetState;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public void setTargetState(IFlowState targetState) {
		this.targetState = targetState;
	}

	@Override
	public boolean isHitCountable() {
		return hitCountable;
	}

	@Override
	public Integer getTransitionId() {
		return transitionId;
	}
	
	@Override
	public String toString() {
		return super.toString() + " is [id:" + getTransitionId() + ", name:" + getTransitionName() + "]";
	}

	public void setTransitionId(Integer transitionId) {
		this.transitionId = transitionId;
	}

	public void setHitCountable(boolean hitCountable) {
		this.hitCountable = hitCountable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((transitionName == null) ? 0 : transitionName.hashCode());
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
		FlowTransition other = (FlowTransition) obj;
		if (transitionName == null) {
			if (other.transitionName != null)
				return false;
		} else if (!transitionName.equals(other.transitionName))
			return false;
		return true;
	}	
	
	@Override
	protected void finalize() throws Throwable {
		targetState = null;
		super.finalize();
	}
	
}
