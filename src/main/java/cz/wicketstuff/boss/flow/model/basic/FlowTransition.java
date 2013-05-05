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

import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;

public class FlowTransition implements IFlowTransition {

	private static final long serialVersionUID = 1L;

	private Integer transitionId;
	private String transitionName;
	private IFlowState targetState;
	private boolean hitCountable = true;
	private boolean defaultNext = false;
	private boolean defaultPrevious = false;
	
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
	
	/**
	 * @return the defaultNext
	 */
	@Override
	public boolean isDefaultNext() {
		return defaultNext;
	}

	/**
	 * @param defaultNext the defaultNext to set
	 */
	public void setDefaultNext(boolean defaultNext) {
		this.defaultNext = defaultNext;
	}

	/**
	 * @return the defaultPrevious
	 */
	@Override
	public boolean isDefaultPrevious() {
		return defaultPrevious;
	}

	/**
	 * @param defaultPrevious the defaultPrevious to set
	 */
	public void setDefaultPrevious(boolean defaultPrevious) {
		this.defaultPrevious = defaultPrevious;
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
