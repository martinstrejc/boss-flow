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

import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowJoinState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;

public class FlowJoinState extends FlowVirtualState implements IFlowJoinState {

	private static final long serialVersionUID = 1L;

	private IFlowTransition nextTransition;
	
	public FlowJoinState() {
		super();
	}

	public FlowJoinState(String stateName,
			IFlowTransition nextTransition) {
		super(stateName, null);
		setNextTransition(nextTransition);
	}

	public FlowJoinState(String stateName) {
		super(stateName);
	}
	
	public FlowJoinState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}
	

	public FlowJoinState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowJoinState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}

	@Override
	public IFlowTransition getNextTransition() {
		return nextTransition;
	}

	public void setNextTransition(IFlowTransition nextTransition) {
		this.nextTransition = nextTransition;
	}

	@Override
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		transitionsMap.clear();
		transitionsMap.put(nextTransition.getTransitionName(), nextTransition);
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		nextTransition = null;
		super.finalize();
	}

	
	
}
