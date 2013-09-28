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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowSwitchState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.ISwitchCase;

public class FlowSwitchState extends FlowVirtualState implements IFlowSwitchState {

	private static final long serialVersionUID = 1L;

	private String switchExpression;
	private IFlowTransition defaultTransition;
	private List<ISwitchCase> switchCasesList;
	
	public FlowSwitchState() {
		super();
	}

	public FlowSwitchState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateName, transitionsMap);
	}

	public FlowSwitchState(String stateName) {
		super(stateName);
	}

	public FlowSwitchState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowSwitchState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}

	@Override
	public String getSwitchExpression() {
		return switchExpression;
	}

	@Override
	public Iterator<ISwitchCase> getSwitchCases() {
		return getSwitchCasesList().iterator();
	}

	@Override
	public IFlowTransition getDefaultTransition() {
		return defaultTransition;
	}

	public List<ISwitchCase> getSwitchCasesList() {
		return switchCasesList;
	}

	public void setSwitchCasesList(List<ISwitchCase> switchCasesList) {
		if(switchCasesList instanceof Serializable) {
			this.switchCasesList = switchCasesList;			
		} else {
			this.switchCasesList = new ArrayList<>(switchCasesList);
		}
	}

	public void setSwitchExpression(String switchExpression) {
		this.switchExpression = switchExpression;
	}

	public void setDefaultTransition(IFlowTransition defaultTransition) {
		this.defaultTransition = defaultTransition;
	}
	
	@Override
	public void rebuildTransitionMap(Map<String, IFlowTransition> transitionsMap) {
		transitionsMap.put(getDefaultTransition().getTransitionName(), getDefaultTransition());
		for(ISwitchCase sc : getSwitchCasesList()) {
			transitionsMap.put(sc.getTransition().getTransitionName(), sc.getTransition());
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if(switchCasesList != null) {
			switchCasesList.clear();
			switchCasesList = null;
		}
		defaultTransition = null;
		switchExpression = null;
		super.finalize();
	}

}
