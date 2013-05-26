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
import java.util.Comparator;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.validation.IFlowStateValidator;


/**
 * Interface represents main business logic of the flow. 
 * It is just a facade of many interface that adds some functionality.
 * 
 * @author Martin Strejc
 *
 * @param <T> type of flow payload
 */
public interface IFlowProcessor<T extends Serializable> extends 
	IFlowListener<T>,
	IFlowStateValidationListener<T>, 
	IFlowStateChangeListener<T>,
	IFlowTransitionChangeListener<T>,
	IFlowStateValidator<T>,
	IFlowStateProcessor<T>,
	IFlowCarterFactory<T>,
	IFlowStateDataFactory,
	IFlowTransitionResolver<T>,
	IFlowTransitionChecker<T>,
	IFlowStateResolver,
	IFlowStatePersister<T>,
	IFlowStateOrdinalComparator {	

	/**
	 * Returns id of the flow. It is used when more flow type are present.
	 * For example an ID defined in XML.
	 * 
	 * @return
	 */
	public Integer getFlowId();

	/**
	 * Returns flow name. For example a name defined in the XML.
	 * 
	 * @return flow name 
	 */
	public String getFlowName();

	/**
	 * Initialize a new flow in a default initial state. 
	 * Exception is thrown when none initial state exists. 
	 * 
	 * @param flowProcessId
	 * @return
	 * @throws FlowException
	 */
	public IFlowCarter<T> initFlow(Long flowProcessId) throws FlowException;
	
	/**
	 * Initialize a new flow in a default initial state. 
	 * Exception is thrown when none initial state exists. 
	 * 
	 * @param flowProcessId
	 * @param payload
	 * @return
	 * @throws FlowException
	 */
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload) throws FlowException;

	/**
	 * Initialize a new flow and start it in an initial state. 
	 * Exception is thrown when the required initial state is not marked as initial.
	 * 
	 * @param flowProcessId
	 * @param payload
	 * @param initialState
	 * @return
	 * @throws FlowException
	 */
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload, IFlowState initialState) throws FlowException;

	/**
	 * Initialize a new flow and start it in an initial state, the inital state is found by its name.
	 * 
	 * @param flowProcessId
	 * @param payload
	 * @param initialStateName
	 * @return
	 * @throws FlowException
	 */
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload, String initialStateName) throws FlowException;

	/**
	 * Return default flow initial state or null if none initial state exists.
	 * 
	 * @return default initial state or null
	 */
	public IFlowState getDefaultInitialState();
		
	/**
	 * Invoke the given transition on the current flow state using whole flow object.
	 *  
	 * @param flow
	 * @param transition
	 * @return
	 * @throws FlowException
	 */
	public boolean invokeTransition(IFlowCarter<T> flow, IFlowTransition transition) throws FlowException;

	/**
	 * Invoke the given transition on the current flow state using whole flow object.
	 * It tries to find the transition by name.
	 * 
	 * @param flow
	 * @param transitionName
	 * @return
	 * @throws FlowException
	 */
	public boolean invokeTransition(IFlowCarter<T> flow, String transitionName) throws FlowException;

	/**
	 * Invoke the default next transition and shift the flow.
	 * Exception is thrown when none next transition is defined for the current state.
	 * 
	 * @param flow
	 * @return
	 * @throws FlowException
	 */
	public boolean invokeDefaultNextTransition(IFlowCarter<T> flow) throws FlowException;

	/**
	 * Invoke the default previous transition and shift the flow.
	 * Exception is thrown when none previous transition is defined for the current state.
	 * 
	 * @param flow
	 * @return
	 * @throws FlowException
	 */
	public boolean invokeDefaultPreviousTransition(IFlowCarter<T> flow) throws FlowException;
	
	/**
	 * Force change the flow state. 
	 * It is NOT RECOMMENDED to invoke this method if you exactly don't know what are you doing.
	 * 
	 * @param flow
	 * @param flowState
	 * @throws FlowException
	 */
	public void forceSetFlowState(IFlowCarter<T> flow, IFlowState flowState) throws FlowException;
	
	/**
	 * Force change the flow state.
	 * It is NOT RECOMMENDED to invoke this method if you exactly don't know what are you doing.
	 * 
	 * @param flow
	 * @param flowState
	 * @param stateData
	 * @param countHit
	 * @throws FlowException
	 */
	public void forceSetFlowState(IFlowCarter<T> flow, IFlowState flowState, Serializable stateData, boolean countHit) throws FlowException;
	
	/**
	 * Compare a testedState and returns true if the state is a current state of flow.
	 * 
	 * @param flow
	 * @param testedState
	 * @return
	 */
	public boolean isCurrentState(IFlowCarter<T> flow, IFlowState testedState);

	/**
	 * Compare a testState by its name and returns true if the state is a current state of flow.
	 * 
	 * @param flow
	 * @param testedFlowStateName
	 * @return
	 */
	public boolean isCurrentState(IFlowCarter<T> flow, String testedFlowStateName);
	
	/**
	 * Return the ordinal comparator of low states. Be aware, states needn't be oridnal in all cases.
	 * 
	 * @return
	 */
	public Comparator<IFlowState> getStateOrdinalComparator();
	
}
