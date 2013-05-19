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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.processor.FlowAlreadyFinishedException;
import cz.wicketstuff.boss.flow.processor.FlowInitializationException;
import cz.wicketstuff.boss.flow.processor.FlowMaxHitsReachedException;
import cz.wicketstuff.boss.flow.processor.FlowTransitionIsRunningException;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;

public abstract class AbstractFlowProcessor<T extends Serializable> implements IFlowProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer maxFlowHits;
	
	private static final Logger log = LoggerFactory.getLogger(AbstractFlowProcessor.class);
	
	public AbstractFlowProcessor() {
	}

	@Override
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload) throws FlowException {
		return initFlow(flowProcessId, payload, getDefaultInitialState());
	}

	@Override
	public IFlowCarter<T> initFlow(Long flowProcessId, T payload, IFlowState initialState) throws FlowException {
		if(!initialState.isInitialState()) {
			throw new FlowInitializationException("Cannot initialize flow, '" + initialState.getStateName() + "' is not an initial state of flow '" + getFlowName() + "'.");
		}
		IFlowCarter<T> flow = createFlowCarter(flowProcessId, initialState);
		flow.setPayload(payload);
		onFlowInitialized(flow);
		if(initialState.isRequireStateData()) {
			flow.setStateData(createStateData(initialState));			
		}
		onStateEntry(flow);
		log.trace("State " + flow.getCurrentState().getStateName() + " persistable " + flow.getCurrentState().isPersistableState());
		if(flow.getCurrentState().isPersistableState()) {
			persistFlowState(flow);
		}
		processState(flow);
		IFlowTransition nextTransition = flow.getNextTransition(); 
		if(nextTransition != null) {
			invokeTransition(flow, nextTransition);
		}
		return flow;
	}
	
	@Override
	public boolean invokeTransition(IFlowCarter<T> flow, IFlowTransition transition)
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
				onStateEntry(flow);
				if(flow.getCurrentState().isPersistableState()) {
					persistFlowState(flow);
				}
				if(isMaxHitsReached(flow)) {
					throw new FlowMaxHitsReachedException(getMaxFlowHits(), flow.getStateHit());
				}
				processState(flow);
			} while (flow.getNextTransition() != null);
			flow.setFlowProcessed(false);	
			if(flow.getCurrentState().isFinalState()) {
				onFlowFinished(flow);
			}
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
	
}
