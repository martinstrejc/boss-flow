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
package cz.wicketstuff.boss.flow.processor;

import java.io.Serializable;

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
	IFlowTransitionChecker<T> {	

	Integer getFlowId();

	String getFlowName();
	
	IFlowCarter<T> initFlow(Long flowProcessId, T payload) throws FlowException;

	IFlowCarter<T> initFlow(Long flowProcessId, T payload, IFlowState startState) throws FlowException;

	IFlowState getDefaultStartState();
		
	boolean runTransition(IFlowCarter<T> flow, IFlowTransition transition) throws FlowException;
	
	boolean isCurrentState(IFlowCarter<T> flow, IFlowState testedState);

	boolean isCurrentState(IFlowCarter<T> flow, String testedFlowStateName);
	
}
