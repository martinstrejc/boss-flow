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

import java.util.HashMap;
import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowConditionState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;

public class FlowConditionState extends FlowVirtualState implements IFlowConditionState {

	private static final long serialVersionUID = 1L;

	private String conditionExpression;
	private IFlowTransition thenTransition;
	private IFlowTransition elseTransition;
	
	public FlowConditionState() {
		this(null);
	}

	public FlowConditionState(String stateName) {
		super(stateName, new HashMap<String, IFlowTransition>());
	}
	
	public FlowConditionState(String conditionExpression,
			IFlowTransition thenTransition, IFlowTransition elseTransition) {
		super(null, new HashMap<String, IFlowTransition>());
		this.conditionExpression = conditionExpression;
		this.thenTransition = thenTransition;
		this.elseTransition = elseTransition;
		rebuildTransitionMap();
	}

	public FlowConditionState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowConditionState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}

	public FlowConditionState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}

	@Override
	public String getConditionExpression() {
		return conditionExpression;
	}

	@Override
	public IFlowTransition getThenTransition() {
		return thenTransition;
	}

	@Override
	public IFlowTransition getElseTransition() {
		return elseTransition;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	public void setThenTransition(IFlowTransition thenTransition) {
		this.thenTransition = thenTransition;
		rebuildTransitionMap();
	}

	public void setElseTransition(IFlowTransition elseTransition) {
		this.elseTransition = elseTransition;
		rebuildTransitionMap();
	}
	
	@Override
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		transitionsMap.clear();
		if(getThenTransition() != null) {
			transitionsMap.put(getThenTransition().getTransitionName(), getThenTransition());			
		}
		if(getElseTransition() != null) {
			transitionsMap.put(getElseTransition().getTransitionName(), getElseTransition());
		}
	}
		
	@Override
	protected void finalize() throws Throwable {
		conditionExpression = null;
		elseTransition = null;
		thenTransition = null;
		super.finalize();
	}

}
