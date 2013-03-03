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

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.processor.FlowPersistingException;
import cz.wicketstuff.boss.flow.processor.IFlowCarterFactory;
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
	
	private IFlowStateChangeListener<T> stateChangeListener;
	private IFlowTransitionChangeListener<T> transitionChangeListener;
	private IFlowStateValidationListener<T> stateValidationListener;
	
	private IFlowStateResolver stateResolver;
	private IFlowTransitionResolver<T> transitionResolver;
	
	private IFlowStatePersister<T> flowStatePersister;
	
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
		return getCarterFactory().createFlowCarter(flowProcessId, initialState);
	}

	@Override
	public IFlowValidation validateState(IFlowCarter<T> flow) {
		return getStateValidator().validateState(flow);
	}
	
	@Override
	public void processState(IFlowCarter<T> flow) throws FlowException {
		getStateProcessor().processState(flow);
		
	}

	@Override
	public void onStateValid(IFlowCarter<T> flow) {
		if(getStateValidationListener() != null)
			getStateValidationListener().onStateValid(flow);
	}

	@Override
	public void onStateInvalid(IFlowCarter<T> flow) {
		if(getStateValidationListener() != null)
			getStateValidationListener().onStateInvalid(flow);
		
	}

	@Override
	public void onTransitionStart(IFlowCarter<T> flow) {
		if(getTransitionChangeListener() != null)
			getTransitionChangeListener().onTransitionStart(flow);
	}

	@Override
	public void onTransitionFinished(IFlowCarter<T> flow) {
		if(getTransitionChangeListener() != null)
			getTransitionChangeListener().onTransitionFinished(flow);
	}

	@Override
	public void onStateEntry(IFlowCarter<T> flow) {
		if(getStateChangeListener() != null)
			getStateChangeListener().onStateEntry(flow);
	}

	@Override
	public void onStateLeaving(IFlowCarter<T> flow) {
		if(getStateChangeListener() != null)
			getStateChangeListener().onStateLeaving(flow);
	}

	@Override
	public IFlowState resolveState(String stateName)
			throws NoSuchStateException {
		return getStateResolver().resolveState(stateName);
	}

	@Override
	public IFlowTransition resolveTransition(IFlowCarter<T> flow, String transitionName)
			throws NoSuchTransitionException {
		return getTransitionResolver().resolveTransition(flow, transitionName);
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
		return getStateDataFactory().createStateData(flowState);
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

	public IFlowStateResolver getStateResolver() {
		return stateResolver;
	}

	public void setStateResolver(IFlowStateResolver stateResolver) {
		this.stateResolver = stateResolver;
	}

	public IFlowTransitionResolver<T> getTransitionResolver() {
		return transitionResolver;
	}

	public void setTransitionResolver(IFlowTransitionResolver<T> transitionResolver) {
		this.transitionResolver = transitionResolver;
	}
	
	public String getDefaultInitialStateName() {
		return getDefaultInitialState().getStateName();
	}

	public void setDefaultInitialStateName(String defaultInitialStateName) throws NoSuchStateException {
		setDefaultInitialState(getStateResolver().resolveState(defaultInitialStateName));
	}
	
	public IFlowStatePersister<T> getFlowStatePersister() {
		return flowStatePersister;
	}

	public void setFlowStatePersister(IFlowStatePersister<T> flowStatePersister) {
		this.flowStatePersister = flowStatePersister;
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

	public void onFlowInitialized(IFlowCarter<T> flow) {
		
	}

	@Override
	public void persistFlowState(IFlowCarter<T> flow) throws FlowPersistingException {
		IFlowStatePersister<T> p = getFlowStatePersister();
		if(p != null) {
			p.persistFlowState(flow);
		}		
	}

}
