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

import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.IFlowViewState;

public class FlowViewState extends FlowRealState implements IFlowViewState {

	private static final long serialVersionUID = 1L;

	private String viewName;
	
	public FlowViewState() {
		this(null);
	}

	public FlowViewState(String stateName) {
		super(stateName);
	}
	

	public FlowViewState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}

	public FlowViewState(String stateName,
			Map<String, IFlowTransition> transitionsMap, String viewName) {
		super(stateName, transitionsMap);
		this.viewName = viewName;
	}

	public FlowViewState(String stateName, String viewName) {
		this(stateName, null, viewName);
	}

	@Override
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public FlowViewState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowViewState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}
	
	
	
}
