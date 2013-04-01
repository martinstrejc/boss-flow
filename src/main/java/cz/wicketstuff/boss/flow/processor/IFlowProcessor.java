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
 * Service interface of payment flow.
 * This interface represents business logic of the flow.  
 * 
 * @author Martin Strejc
 *
 * @param <T> stepData class
 */
public interface IFlowProcessor<T extends Serializable> extends 
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

	Integer getFlowId();

	String getFlowName();
	
	IFlowCarter<T> initFlow(Long flowProcessId, T payload) throws FlowException;

	IFlowCarter<T> initFlow(Long flowProcessId, T payload, IFlowState initialState) throws FlowException;

	IFlowCarter<T> initFlow(Long flowProcessId, T payload, String initialStateName) throws FlowException;

	IFlowState getDefaultInitialState();
		
	boolean invokeTransition(IFlowCarter<T> flow, IFlowTransition transition) throws FlowException;

	boolean invokeTransition(IFlowCarter<T> flow, String transitionName) throws FlowException;

	boolean isCurrentState(IFlowCarter<T> flow, IFlowState testedState);

	boolean isCurrentState(IFlowCarter<T> flow, String testedFlowStateName);
	
	Comparator<IFlowState> getStateOrdinalComparator();
	
}
