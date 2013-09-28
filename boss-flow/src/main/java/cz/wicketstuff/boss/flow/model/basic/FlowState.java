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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cz.wicketstuff.boss.flow.model.IFlowCategory;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.util.FlowMatcherHelper;

public class FlowState implements IFlowState {

	private static final long serialVersionUID = 1L;

	private Integer stateId;
	private String stateName;
	private Map<String, IFlowTransition> transitionsMap;
	private boolean finalState = false;
	private boolean initialState = false;
	private boolean requireStateData;
	private boolean stateValidatable;
	private boolean persistableState;
	private Integer order;
	private List<IFlowCategory> flowCategories;

	public FlowState() {
		this(null, null);
	}

	public FlowState(String stateName) {
		this(null, stateName, new HashMap<String, IFlowTransition>());
	}

	public FlowState(Integer stateId, String stateName) {
		this(stateId, stateName, new HashMap<String, IFlowTransition>());
	}

	public FlowState(Integer stateId, String stateName, Map<String, IFlowTransition> transitionsMap) {
		this.stateName = stateName;
		this.stateId = stateId;
		this.transitionsMap = transitionsMap;
	}
	
	public void putFlowTransition(IFlowTransition transition) {
		getTransitionsMap().put(transition.getTransitionName(), transition);
	}

	@Override
	public String getStateName() {
		return stateName;
	}

	@Override
	public boolean isFinalState() {
		return finalState;
	}

	@Override
	public boolean isInitialState() {
		return initialState;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}

	public void setInitialState(boolean initialState) {
		this.initialState = initialState;
	}

	@Override
	public boolean isPersistableState() {
		return persistableState;
	}

	public void setPersistableState(boolean persistableState) {
		this.persistableState = persistableState;
	}

	@Override
	public Integer getStateId() {
		return stateId;
	}

	@Override
	public boolean isStateValidatable() {
		return stateValidatable;
	}

	@Override
	public boolean isRequireStateData() {
		return requireStateData;
	}

	@Override
	public Iterator<IFlowTransition> getAvailableTransitions() {
		return getTransitionsMap().values().iterator();
	}

	/**
	 * @return the order
	 */
	@Override
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setTransitionsMap(Map<String, IFlowTransition> transitionsMap) {
		this.transitionsMap = transitionsMap;		
	}

	public Map<String, IFlowTransition> getTransitionsMap() {
		if(transitionsMap == null) {
			transitionsMap = createTransitionMap();
			rebuildTransitionMap(transitionsMap);
		}
		return transitionsMap;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public void setStateValidatable(boolean stateValidatable) {
		this.stateValidatable = stateValidatable;
	}

	public void setRequireStateData(boolean requireStateData) {
		this.requireStateData = requireStateData;
	}

	public Map<String, IFlowTransition> createTransitionMap() {
		return new HashMap<String, IFlowTransition>();
	}
	
	@Override
	public String toString() {
		return super.toString() + " is [id:" + getStateId() + ", name:" + getStateName() + "]";
	}

	public void rebuildTransitionMap() {
		if(transitionsMap == null) {
			transitionsMap = createTransitionMap();
		}	
		rebuildTransitionMap(transitionsMap);
	}
	
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stateName == null) ? 0 : stateName.hashCode());
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
		FlowState other = (FlowState) obj;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		return true;
	}

	@Override
	protected void finalize() throws Throwable {
		if(transitionsMap != null) {
			transitionsMap.clear();
			transitionsMap = null;
		}
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
