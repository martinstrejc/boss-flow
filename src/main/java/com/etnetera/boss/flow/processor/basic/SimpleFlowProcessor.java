/*
 * Et netera, http://boss.etnetera.cz - Copyright (C) 2012 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 */
package com.etnetera.boss.flow.processor.basic;

import java.io.Serializable;

import com.etnetera.boss.flow.FlowException;
import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.model.IFlowTransition;
import com.etnetera.boss.flow.processor.IFlowCarterFactory;
import com.etnetera.boss.flow.processor.IFlowStateChangeListener;
import com.etnetera.boss.flow.processor.IFlowStateDataFactory;
import com.etnetera.boss.flow.processor.IFlowStateProcessor;
import com.etnetera.boss.flow.processor.IFlowStateResolver;
import com.etnetera.boss.flow.processor.IFlowStateValidationListener;
import com.etnetera.boss.flow.processor.IFlowTransitionChangeListener;
import com.etnetera.boss.flow.processor.IFlowTransitionResolver;
import com.etnetera.boss.flow.processor.INamedFlowProcessor;
import com.etnetera.boss.flow.processor.NoSuchStateException;
import com.etnetera.boss.flow.processor.NoSuchTransitionException;
import com.etnetera.boss.flow.processor.StateDataException;
import com.etnetera.boss.flow.util.Comparators;
import com.etnetera.boss.flow.util.listener.IPriority;
import com.etnetera.boss.flow.validation.IFlowStateValidator;
import com.etnetera.boss.flow.validation.IFlowValidation;

public class SimpleFlowProcessor<T extends Serializable> extends AbstractFlowProcessor<T> 
	implements INamedFlowProcessor<T>, IFlowStateResolver {

	private static final long serialVersionUID = 1L;
	
	private Integer flowId;
	private String flowName;
	private IFlowState defaultStartState;
	
	private IFlowCarterFactory<T> carterFactory;
	private IFlowStateDataFactory stateDataFactory;
	
	private IFlowStateProcessor<T> stateProcessor;
	private IFlowStateValidator<T> stateValidator;
	
	private IFlowStateChangeListener<T> stateChangeListener;
	private IFlowTransitionChangeListener<T> transitionChangeListener;
	private IFlowStateValidationListener<T> stateValidationListener;
	
	private IFlowStateResolver stateResolver;
	private IFlowTransitionResolver<T> transitionResolver;
	
	private int priority = 0;
	
	public SimpleFlowProcessor() {
		super();
	}

	@Override
	public IFlowCarter<T> startFlow(Long flowProcessId, T payload, String startStateName) throws FlowException {
		return initFlow(flowProcessId, payload, getStateResolver().resolveState(startStateName));
	}
	
	@Override
	public boolean invokeTransition(IFlowCarter<T> flow, String transitionName)
			throws FlowException {
		return runTransition(flow, resolveTransition(flow, transitionName));
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
	public IFlowState getDefaultStartState() {
		return defaultStartState;
	}
	
	public void setDefaultStartState(IFlowState defaultStartState) {
		this.defaultStartState = defaultStartState;
	}

	@Override
	public IFlowCarter<T> createFlowCarter(Long flowProcessId, IFlowState startState) throws FlowException {
		return getCarterFactory().createFlowCarter(flowProcessId, startState);
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
	
	public String getDefaultStartStateName() {
		return getDefaultStartState().getStateName();
	}

	public void setDefaultStartStateName(String defaultStartStateName) throws NoSuchStateException {
		setDefaultStartState(getStateResolver().resolveState(defaultStartStateName));
	}
	@Override
	protected void finalize() throws Throwable {
		carterFactory = null;
		defaultStartState = null;
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

}
