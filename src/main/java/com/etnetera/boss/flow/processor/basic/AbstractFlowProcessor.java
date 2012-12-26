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
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etnetera.boss.flow.FlowException;
import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.model.IFlowTransition;
import com.etnetera.boss.flow.processor.FlowAlreadyFinishedException;
import com.etnetera.boss.flow.processor.FlowInitializationException;
import com.etnetera.boss.flow.processor.FlowMaxHitsReachedException;
import com.etnetera.boss.flow.processor.FlowTransitionIsRunningException;
import com.etnetera.boss.flow.processor.IFlowProcessor;
import com.etnetera.boss.flow.processor.NoSuchTransitionException;

public abstract class AbstractFlowProcessor<T extends Serializable> implements IFlowProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer maxFlowHits;
	
	private static final Logger log = LoggerFactory.getLogger(AbstractFlowProcessor.class);
	
	public AbstractFlowProcessor() {
	}

	@Override
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload) throws FlowException {
		return initFlow(flowProcessId, payload, getDefaultStartState());
	}

	@Override
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload, IFlowState startState) throws FlowException {
		if(!startState.isStartState()) {
			throw new FlowInitializationException("Cannot initialize flow, '" + startState.getStateName() + "' is not a start state of flow '" + getFlowName() + "'.");
		}
		IFlowCarter<T> flow = createFlowCarter(flowProcessId, startState);
		flow.setPayload(payload);
		onFlowInitialized(flow);
		if(startState.isRequireStateData()) {
			flow.setStateData(createStateData(startState));			
		}
		onStateEntry(flow);
		processState(flow);
		IFlowTransition nextTransition = flow.getNextTransition(); 
		if(nextTransition != null) {
			runTransition(flow, nextTransition);
		}
		return flow;
	}
	
	@Override
	public boolean runTransition(IFlowCarter<T> flow, IFlowTransition transition)
			throws FlowException {
		if(flow.isFlowProcessed()) {
			throw new FlowTransitionIsRunningException("Transition " + transition.getTransitionName() + " is currently running on " + flow.getCurrentState().getStateName());
		}
		flow.setFlowProcessed(true);		
		try {			
			if(log.isDebugEnabled()) {
				log.debug("Running transition '" + transition.getTransitionName() + "' on step '" + flow.getCurrentState().getStateName() + "'");			
			}
			flow.setNextTransition(transition);
			int stateHit = flow.getStateHit();
			
			do {
				if(flow.getCurrentState().isFinalState()) {
					throw new FlowAlreadyFinishedException("The state '" + flow.getCurrentState().getStateName() + "' of flow '" + getFlowName() + "' is a final state, no transition is allowed here.");
				}
				
				if(!checkTransition(flow, flow.getNextTransition())) {
					throw new NoSuchTransitionException("Transition '" + flow.getNextTransition().getTransitionName() + "' is not a valid transition of state '" + flow.getCurrentState().getStateName() + "'.");
				}
				
				if(flow.getCurrentState().isStateValidatable()) {
					if(validateState(flow).isValid()) {
						onStateValid(flow);				
					} else {
						onStateInvalid(flow);
						flow.setNextTransition(null);
						return false;
					}
				}
				onTransitionStart(flow);
				onStateLeaving(flow);
				onTransitionFinished(flow);
				flow.shiftFlow();
				if(isMaxHitsReached(flow)) {
					throw new FlowMaxHitsReachedException(getMaxFlowHits(), flow.getStateHit());
				}
				onStateEntry(flow);
				processState(flow);
			} while (flow.getNextTransition() != null);
			flow.setFlowProcessed(false);			
			return flow.getStateHit() == stateHit;
		} catch (Exception e) {
			flow.setFlowProcessed(false);
			throw e;
		}
	}
	
	public boolean isMaxHitsReached(IFlowCarter<T> flow) {
		Integer max = getMaxFlowHits(); 
		if(max == null) {
			return false;
		}
		return max <= flow.getStateHit();
	}

	@Override
	public boolean isCurrentState(IFlowCarter<T> flow, IFlowState testedFlowState) {
		return flow.getCurrentState().equals(testedFlowState);
	}

	@Override
	public boolean isCurrentState(IFlowCarter<T> flow, String testedFlowStateName) {
		return flow.getCurrentState().getStateName().equals(testedFlowStateName);
	}

	@Override
	public boolean checkTransition(IFlowCarter<T> flow,
			IFlowTransition nextTransition) {
		for(Iterator<IFlowTransition> it = flow.getCurrentState().getAvailableTransitions(); it.hasNext();) {
			IFlowTransition t = it.next();
			if(nextTransition.equals(t)) {
				return true;				
			}
		}
		return false;
	}

	public Integer getMaxFlowHits() {
		return maxFlowHits;
	}

	public void setMaxFlowHits(Integer maxFlowHits) {
		this.maxFlowHits = maxFlowHits;
	}
	
	abstract public void onFlowInitialized(IFlowCarter<T> flow);

}
