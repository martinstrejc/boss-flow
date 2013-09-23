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
import java.util.Comparator;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.processor.FlowListenerException;
import cz.wicketstuff.boss.flow.processor.FlowPersistingException;
import cz.wicketstuff.boss.flow.processor.FlowRestoringException;
import cz.wicketstuff.boss.flow.processor.FlowStateListenerException;
import cz.wicketstuff.boss.flow.processor.FlowTransitionListenerException;
import cz.wicketstuff.boss.flow.processor.FlowValidationListenerException;
import cz.wicketstuff.boss.flow.processor.IFlowCarterFactory;
import cz.wicketstuff.boss.flow.processor.IFlowListener;
import cz.wicketstuff.boss.flow.processor.IFlowPersisterListener;
import cz.wicketstuff.boss.flow.processor.IFlowStateChangeListener;
import cz.wicketstuff.boss.flow.processor.IFlowStateDataFactory;
import cz.wicketstuff.boss.flow.processor.IFlowStatePersister;
import cz.wicketstuff.boss.flow.processor.IFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStateResolver;
import cz.wicketstuff.boss.flow.processor.IFlowStateValidationListener;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionChangeListener;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionResolver;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;
import cz.wicketstuff.boss.flow.processor.StateDataException;
import cz.wicketstuff.boss.flow.processor.UnsupportedStateOperationException;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.listener.IPriority;
import cz.wicketstuff.boss.flow.validation.IFlowStateValidator;
import cz.wicketstuff.boss.flow.validation.IFlowValidation;

public class SimpleFlowProcessor<T extends Serializable> extends AbstractFlowProcessor<T> {

	private static final long serialVersionUID = 1L;
	
	private Integer flowId;
	private String flowName;
	private IFlowState defaultInitialState;
	
	private IFlowCarterFactory<T> carterFactory;
	private IFlowStateDataFactory stateDataFactory;
	
	private IFlowStateProcessor<T> stateProcessor;
	private IFlowStateValidator<T> stateValidator;
	
	private IFlowListener<T> flowListener;
	private IFlowPersisterListener<T> persisterListener;
	private IFlowStateChangeListener<T> stateChangeListener;
	private IFlowTransitionChangeListener<T> transitionChangeListener;
	private IFlowStateValidationListener<T> stateValidationListener;
	
	private IFlowStateResolver<T> stateResolver;
	
	private IFlowTransitionResolver<T> transitionResolver;
	
	private IFlowStatePersister<T> flowStatePersister;
	
	private Comparator<IFlowState> stateOrdinalComparator;
	
	private int priority = 0;
	
	public SimpleFlowProcessor() {
		super();
	}

	@Override
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload, String initialStateName) throws FlowException {
		return initFlow(flowProcessId, payload, getStateResolver().resolveState(initialStateName));
	}

	@Override
	public boolean invokeTransition(IFlowCarter<T> flow, String transitionName)
			throws FlowException {
		return invokeTransition(flow, resolveTransition(flow, transitionName));
	}


	@Override
	public Integer getFlowId() {
		return flowId;
	}

	public void setFlowId(Integer flowId) {
		this.flowId = flowId;
	}

	@Override
	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Override
	public IFlowState getDefaultInitialState() {
		return defaultInitialState;
	}
	
	public void setDefaultInitialState(IFlowState defaultInitialState) {
		this.defaultInitialState = defaultInitialState;
	}

	@Override
	public IFlowCarter<T> createFlowCarter(Long flowProcessId, IFlowState initialState) throws FlowException {
		return carterFactory.createFlowCarter(flowProcessId, initialState);
	}

	@Override
	public IFlowValidation validateState(IFlowCarter<T> flow) {
		return stateValidator.validateState(flow);
	}
	
	@Override
	public void processState(IFlowCarter<T> flow) throws FlowException {
		stateProcessor.processState(flow);
		
	}

	@Override
	public void onStateValid(IFlowCarter<T> flow) throws FlowValidationListenerException {
		if(stateValidationListener != null)
			stateValidationListener.onStateValid(flow);
	}

	@Override
	public void onStateInvalid(IFlowCarter<T> flow) throws FlowValidationListenerException {
		if(stateValidationListener != null)
			stateValidationListener.onStateInvalid(flow);
		
	}
	
	@Override
	public void onFlowInitialized(IFlowCarter<T> flow) throws FlowListenerException {
		if(flowListener != null) {
			flowListener.onFlowInitialized(flow);
		}
	}

	@Override
	public void onFlowFinished(IFlowCarter<T> flow) throws FlowListenerException {
		if(flowListener != null) {
			flowListener.onFlowFinished(flow);
		}
	}

	@Override
	public void onTransitionStart(IFlowCarter<T> flow, IFlowTransition flowTransition) throws FlowTransitionListenerException {
		if(transitionChangeListener != null)
			transitionChangeListener.onTransitionStart(flow, flowTransition);
	}

	@Override
	public void onTransitionFinished(IFlowCarter<T> flow, IFlowTransition flowTransition) throws FlowTransitionListenerException {
		if(transitionChangeListener != null)
			transitionChangeListener.onTransitionFinished(flow, flowTransition);
	}

	@Override
	public void onStateEntry(IFlowCarter<T> flow, IFlowState flowState) throws FlowStateListenerException {
		if(stateChangeListener != null)
			stateChangeListener.onStateEntry(flow, flowState);
	}

	@Override
	public void onStateLeaving(IFlowCarter<T> flow, IFlowState flowState) throws FlowStateListenerException {
		if(stateChangeListener != null)
			stateChangeListener.onStateLeaving(flow, flowState);
	}

	@Override
	public IFlowState resolveState(String stateName)
			throws NoSuchStateException {
		return stateResolver.resolveState(stateName);
	}

	@Override
	public String resolveCurrentViewName(IFlowCarter<T> flowCarter) {
		return stateResolver.resolveCurrentViewName(flowCarter);
	}

	@Override
	public IFlowTransition resolveTransition(IFlowCarter<T> flow, String transitionName)
			throws NoSuchTransitionException {
		return transitionResolver.resolveTransition(flow, transitionName);
	}

	@Override
	public IFlowTransition resolveNextTransition(IFlowCarter<T> flow) throws NoSuchTransitionException,
			UnsupportedStateOperationException {
		return transitionResolver.resolveNextTransition(flow);
	}

	@Override
	public IFlowTransition resolvePreviousTransition(IFlowCarter<T> flow) throws NoSuchTransitionException,
			UnsupportedStateOperationException {
		return transitionResolver.resolvePreviousTransition(flow);
	}


	public IFlowStateChangeListener<T> getStateChangeListener() {
		return stateChangeListener;
	}

	public void setStateChangeListener(
			IFlowStateChangeListener<T> stateChangeListener) {
		this.stateChangeListener = stateChangeListener;
	}

	public IFlowTransitionChangeListener<T> getTransitionChangeListener() {
		return transitionChangeListener;
	}

	public void setTransitionChangeListener(
			IFlowTransitionChangeListener<T> transitionChangeListener) {
		this.transitionChangeListener = transitionChangeListener;
	}

	public IFlowStateValidationListener<T> getStateValidationListener() {
		return stateValidationListener;
	}

	public void setStateValidationListener(
			IFlowStateValidationListener<T> stateValidationListener) {
		this.stateValidationListener = stateValidationListener;
	}

	public IFlowStateProcessor<T> getStateProcessor() {
		return stateProcessor;
	}

	public void setStateProcessor(IFlowStateProcessor<T> stateProcessor) {
		this.stateProcessor = stateProcessor;
	}

	public IFlowStateValidator<T> getStateValidator() {
		return stateValidator;
	}

	public void setStateValidator(IFlowStateValidator<T> stateValidator) {
		this.stateValidator = stateValidator;
	}

	@Override
	public Serializable createStateData(IFlowState flowState)
			throws NoSuchStateException, StateDataException {
		if(stateDataFactory == null) {
			throw new NullPointerException("Cannot create stateData, because factory is NULL");
		}
		return stateDataFactory.createStateData(flowState);
	}

	public IFlowStateDataFactory getStateDataFactory() {
		return stateDataFactory;
	}

	public void setStateDataFactory(IFlowStateDataFactory stateDataFactory) {
		this.stateDataFactory = stateDataFactory;
	}

	public IFlowCarterFactory<T> getCarterFactory() {
		return carterFactory;
	}

	public void setCarterFactory(IFlowCarterFactory<T> carterFactory) {
		this.carterFactory = carterFactory;
	}

	public IFlowStateResolver<T> getStateResolver() {
		return stateResolver;
	}

	public void setStateResolver(IFlowStateResolver<T> stateResolver) {
		this.stateResolver = stateResolver;
	}

	public IFlowTransitionResolver<T> getTransitionResolver() {
		return transitionResolver;
	}

	public void setTransitionResolver(IFlowTransitionResolver<T> transitionResolver) {
		this.transitionResolver = transitionResolver;
	}
	
	public String getDefaultInitialStateName() {
		return defaultInitialState.getStateName();
	}

	public void setDefaultInitialStateName(String defaultInitialStateName) throws NoSuchStateException {
		defaultInitialState = getStateResolver().resolveState(defaultInitialStateName);
	}
	
	public IFlowStatePersister<T> getFlowStatePersister() {
		return flowStatePersister;
	}

	public void setFlowStatePersister(IFlowStatePersister<T> flowStatePersister) {
		this.flowStatePersister = flowStatePersister;
	}
	
	/**
	 * @return the flowListener
	 */
	public IFlowListener<T> getFlowListener() {
		return flowListener;
	}

	/**
	 * @param flowListener the flowListener to set
	 */
	public void setFlowListener(IFlowListener<T> flowListener) {
		this.flowListener = flowListener;
	}
	
	/**
	 * @return the persisterListener
	 */
	public IFlowPersisterListener<T> getPersisterListener() {
		return persisterListener;
	}

	/**
	 * @param persisterListener the persisterListener to set
	 */
	public void setPersisterListener(IFlowPersisterListener<T> persisterListener) {
		this.persisterListener = persisterListener;
	}

	@Override
	public Comparator<IFlowState> getStateOrdinalComparator() {
		return stateOrdinalComparator;
	}
	
	public void setStateOrdinalComparator(
			Comparator<IFlowState> stateOrdinalComparator) {
		this.stateOrdinalComparator = stateOrdinalComparator;
	}

	@Override
	public boolean invokeDefaultNextTransition(IFlowCarter<T> flow)
			throws FlowException {
		return invokeTransition(flow, transitionResolver.resolveNextTransition(flow));
	}

	@Override
	public boolean invokeDefaultPreviousTransition(IFlowCarter<T> flow)
			throws FlowException {
		return invokeTransition(flow, transitionResolver.resolvePreviousTransition(flow));
	}

	@Override
	protected void finalize() throws Throwable {
		carterFactory = null;
		defaultInitialState = null;
		stateChangeListener = null;
		stateDataFactory = null;
		stateProcessor = null;
		stateResolver = null;
		stateValidationListener = null;
		stateValidator = null;
		transitionChangeListener = null;
		transitionResolver = null;
		persisterListener = null;
		flowListener = null;
		super.finalize();
	}

	@Override
	public int compareTo(IPriority o) {
		return Comparators.compareInts(priority, o.getPriority());
	}

	@Override
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareStatesOrdinality(IFlowState state1, IFlowState state2) {
		return stateOrdinalComparator.compare(state1, state2);
	}

	@Override
	public boolean persistFlowState(IFlowCarter<T> flow) throws FlowPersistingException {
		if(flowStatePersister == null) {
			return false;
		}		
		return flowStatePersister.persistFlowState(flow);
	}
	
	@Override
	public IFlowCarter<T> restoreFlowState() throws FlowRestoringException {
		if(flowStatePersister == null) {
			throw new NullPointerException("Cannot restore flow because flowStatePersister is NULL");
		}		
		return flowStatePersister.restoreFlowState();
	}

	@Override
	public void forceSetFlowState(IFlowCarter<T> flow, IFlowState flowState)
			throws FlowException {
		flow.forceSetFlowState(flowState);
	}


}
