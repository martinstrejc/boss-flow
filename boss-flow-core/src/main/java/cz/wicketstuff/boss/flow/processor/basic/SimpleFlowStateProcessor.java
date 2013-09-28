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
package cz.wicketstuff.boss.flow.processor.basic;

import java.io.Serializable;
import java.util.Iterator;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowConditionState;
import cz.wicketstuff.boss.flow.model.IFlowJoinState;
import cz.wicketstuff.boss.flow.model.IFlowSwitchState;
import cz.wicketstuff.boss.flow.model.IFlowViewState;
import cz.wicketstuff.boss.flow.model.ISwitchCase;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.condition.IFlowConditionProcessor;
import cz.wicketstuff.boss.flow.processor.condition.IFlowSwitchProcessor;

public class SimpleFlowStateProcessor<T extends Serializable> extends AbstractFlowStateProcessor<T> {

	private static final long serialVersionUID = 1L;
	
	private IFlowConditionProcessor<T> conditionProcessor;
	private IFlowSwitchProcessor<T> switchProcessor;
	
	public SimpleFlowStateProcessor() {
		this(null);
	}
	
	public SimpleFlowStateProcessor(IFlowProcessor<T> flowProcessor) {
		this(flowProcessor, null, null);
	}

	public SimpleFlowStateProcessor(
			IFlowProcessor<T> flowProcessor,
			IFlowConditionProcessor<T> conditionProcessor,
			IFlowSwitchProcessor<T> switchProcessor) {
		super(flowProcessor);
		this.conditionProcessor = conditionProcessor;
		this.switchProcessor = switchProcessor;
	}

	@Override
	public void processViewState(IFlowCarter<T> flow, IFlowViewState currentState) throws FlowException {
		// do nothing, view state is final
	}

	@Override
	public void processConditionState(IFlowCarter<T> flow, IFlowConditionState currentState) throws FlowException {
		if(getConditionProcessor().ifCondition(currentState.getConditionExpression(), flow)) {
			flow.setNextTransition(currentState.getThenTransition());
		} else {
			flow.setNextTransition(currentState.getElseTransition());
		}
	}

	@Override
	public void processSwitchState(IFlowCarter<T> flow, IFlowSwitchState currentState) throws FlowException {
		IFlowSwitchProcessor<T> sp = getSwitchProcessor();
		String switchValue = sp.resolveSwitchExpression(flow, currentState.getSwitchExpression());
		boolean switched = false;
		for(Iterator<ISwitchCase> ci = currentState.getSwitchCases(); ci.hasNext(); ) {
			ISwitchCase sc = ci.next();
			if(sc.getCaseName().equals(switchValue)) {
				flow.setNextTransition(sc.getTransition());
				switched = true;
				break;
			}
		}
		if(!switched) {
			flow.setNextTransition(currentState.getDefaultTransition());
		}
	}

	@Override
	public void processJoinState(IFlowCarter<T> flow, IFlowJoinState currentState) throws FlowException {
		flow.setNextTransition(currentState.getNextTransition());
	}

	@Override
	public void processUknownState(IFlowCarter<T> flow) throws FlowException {
		// do nothing when unknown state
	}

	public IFlowConditionProcessor<T> getConditionProcessor() {
		return conditionProcessor;
	}

	public void setConditionProcessor(IFlowConditionProcessor<T> conditionProcessor) {
		this.conditionProcessor = conditionProcessor;
	}

	public IFlowSwitchProcessor<T> getSwitchProcessor() {
		return switchProcessor;
	}

	public void setSwitchProcessor(IFlowSwitchProcessor<T> switchProcessor) {
		this.switchProcessor = switchProcessor;
	}
	
	@Override
	protected void finalize() throws Throwable {
		switchProcessor = null;
		conditionProcessor = null;
		super.finalize();
	}

	
}
