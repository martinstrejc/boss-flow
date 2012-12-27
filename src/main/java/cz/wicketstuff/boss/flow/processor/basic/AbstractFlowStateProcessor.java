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
package cz.wicketstuff.boss.flow.processor.basic;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowConditionState;
import cz.wicketstuff.boss.flow.model.IFlowJoinState;
import cz.wicketstuff.boss.flow.model.IFlowRealState;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowSwitchState;
import cz.wicketstuff.boss.flow.model.IFlowViewState;
import cz.wicketstuff.boss.flow.model.IFlowVirtualState;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStateComplexProcessor;

public abstract class AbstractFlowStateProcessor<T extends Serializable> implements IFlowStateComplexProcessor<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final static Logger log = LoggerFactory.getLogger(AbstractFlowStateProcessor.class);
	
	private IFlowProcessor<T> flowProcessor;
	
	public AbstractFlowStateProcessor() {
		this(null);
	}
	
	public AbstractFlowStateProcessor(IFlowProcessor<T> flowProcessor) {
		this.flowProcessor = flowProcessor;
	}

	@Override
	public void processState(IFlowCarter<T> flow) throws FlowException {
		IFlowState state = flow.getCurrentState();
		if(log.isDebugEnabled()) {
			log.debug("Processing state: " + state.getStateName());			
		}
		if(state instanceof IFlowRealState) {
			processRealState(flow, (IFlowRealState) state);
		} else if (state instanceof IFlowVirtualState) {
			processVirtualState(flow, (IFlowVirtualState) state);
		} else {
			processUknownState(flow);
		}
	}

	@Override
	public void processRealState(IFlowCarter<T> flow, IFlowRealState currentState) throws FlowException {
		IFlowState state = flow.getCurrentState();
		if(log.isTraceEnabled()) {
			log.trace("Processing real state: " + state.getStateName());			
		}
		if(state instanceof IFlowViewState) {
			processViewState(flow, (IFlowViewState) state);
		} else {
			processUknownState(flow);
		}
	}

	@Override
	public void processVirtualState(IFlowCarter<T> flow, IFlowVirtualState currentState) throws FlowException {
		IFlowState state = flow.getCurrentState();
		if(log.isTraceEnabled()) {
			log.trace("Processing virtual state: " + state.getStateName());			
		}
		if(state instanceof IFlowConditionState) {
			if(log.isTraceEnabled()) {
				log.trace("Processing condition state: " + state.getStateName());			
			}
			processConditionState(flow, (IFlowConditionState) state);
		} else if(state instanceof IFlowSwitchState) {
			if(log.isTraceEnabled()) {
				log.trace("Processing switch state: " + state.getStateName());			
			}
			processSwitchState(flow, (IFlowSwitchState) state);
		} else if (state instanceof IFlowJoinState) {
			if(log.isTraceEnabled()) {
				log.trace("Processing join state: " + state.getStateName());			
			}
			processJoinState(flow, (IFlowJoinState) state);
		} else {
			if(log.isTraceEnabled()) {
				log.trace("Processing unknown state: " + state.getStateName());			
			}
			processUknownState(flow);
		}
	}

	public IFlowProcessor<T> getFlowProcessor() {
		return flowProcessor;
	}

	public void setFlowProcessor(IFlowProcessor<T> flowProcessor) {
		this.flowProcessor = flowProcessor;
	}

	@Override
	protected void finalize() throws Throwable {
		flowProcessor = null;
		super.finalize();
	}

}
