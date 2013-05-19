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
package cz.wicketstuff.boss.flow.processor;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowConditionState;
import cz.wicketstuff.boss.flow.model.IFlowJoinState;
import cz.wicketstuff.boss.flow.model.IFlowRealState;
import cz.wicketstuff.boss.flow.model.IFlowSwitchState;
import cz.wicketstuff.boss.flow.model.IFlowViewState;
import cz.wicketstuff.boss.flow.model.IFlowVirtualState;

/**
 * Processor of all state types. Processor is responsible for any action of the state including
 * change the next transition of virtual states.
 * 
 * @author Martin Strejc
 *
 * @param <T> type of flow payload
 */
public interface IFlowStateComplexProcessor<T extends Serializable>  extends IFlowStateProcessor<T> {
	
	/**
	 * Process Real State type only
	 * 
	 * @param flow
	 * @param currentState
	 * @throws FlowException
	 */
	void processRealState(IFlowCarter<T> flow, IFlowRealState currentState) throws FlowException;

	/**
	 * Process Virtual State type only
	 * 
	 * @param flow
	 * @param currentState
	 * @throws FlowException
	 */
	void processVirtualState(IFlowCarter<T> flow, IFlowVirtualState currentState) throws FlowException;

	/**
	 * Process View State only
	 * 
	 * @param flow
	 * @param currentState
	 * @throws FlowException
	 */
	void processViewState(IFlowCarter<T> flow, IFlowViewState currentState) throws FlowException;

	/**
	 * Process Condition State only
	 * 
	 * @param flow
	 * @param currentState
	 * @throws FlowException
	 */
	void processConditionState(IFlowCarter<T> flow, IFlowConditionState currentState) throws FlowException;

	/**
	 * Process Switch State only
	 * 
	 * @param flow
	 * @param currentState
	 * @throws FlowException
	 */
	void processSwitchState(IFlowCarter<T> flow, IFlowSwitchState currentState) throws FlowException;

	/**
	 * Process Join State only
	 * 
	 * @param flow
	 * @param currentState
	 * @throws FlowException
	 */
	void processJoinState(IFlowCarter<T> flow, IFlowJoinState currentState) throws FlowException;

	/**
	 * Process all other types than previusly defined.
	 * 
	 * @param flow
	 * @throws FlowException
	 */
	void processUknownState(IFlowCarter<T> flow) throws FlowException;

}
