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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cz.wicketstuff.boss.flow.model.IFlowCategory;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.util.FlowMatcherHelper;

public class FlowTransition implements IFlowTransition {

	private static final long serialVersionUID = 1L;

	private Integer transitionId;
	private String transitionName;
	private IFlowState targetState;
	private boolean hitCountable = true;
	private List<IFlowCategory> flowCategories;
	
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
		if(flowCategories != null) {
			flowCategories.clear();
			flowCategories = null;
		}
		super.finalize();
	}

	@Override
	public List<IFlowCategory> getFlowCategories() {
		return flowCategories;
	}

	@Override
	public boolean matchesAny(String regex) {
		return FlowMatcherHelper.matchesAny(this, regex);
	}

	@Override
	public boolean matchesAny(Pattern pattern) {
		return FlowMatcherHelper.matchesAny(this, pattern);
	}

	@Override
	public boolean matchesNone(String regex) {
		return FlowMatcherHelper.matchesNone(this, regex);
	}

	@Override
	public boolean matchesNone(Pattern pattern) {
		return FlowMatcherHelper.matchesNone(this, pattern);
	}

	/**
	 * @param flowCategories the flowCategories to set
	 */
	public void setFlowCategories(List<IFlowCategory> flowCategories) {
		this.flowCategories = flowCategories;
	}

	public void addFlowCategory(IFlowCategory flowCategory) {
		checkFlowCategoryListPresent();
		flowCategories.add(flowCategory);
	}
	
	protected void checkFlowCategoryListPresent() {
		if(flowCategories == null) {
			flowCategories = newFlowCategoryList();
		}		
	}
	
	protected List<IFlowCategory> newFlowCategoryList() {
		return new ArrayList<>();
	}
	
}