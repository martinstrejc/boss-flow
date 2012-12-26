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
package com.etnetera.boss.flow.processor.ext;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etnetera.boss.flow.annotation.FlowStateEvent.StateEvent;
import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.processor.IFlowStateChangeListener;
import com.etnetera.boss.flow.util.Comparators;
import com.etnetera.boss.flow.util.listener.IPriority;

public abstract class FilteredStateChangeListener<T extends Serializable> implements IFlowStateChangeListener<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStateChangeListener.class);
	
	private StateEvent event;
	
	private String stateNameRegex;
	
	private Class<? extends IFlowState> type;
	
	private int priority;

	public FilteredStateChangeListener() {
	}

	
	public FilteredStateChangeListener(StateEvent event, String stateNameRegex,
			Class<? extends IFlowState> type, int priority) {
		this.event = event;
		this.stateNameRegex = stateNameRegex;
		this.type = type;
		this.priority = priority;
	}


	@Override
	public void onStateEntry(IFlowCarter<T> flow) {
		if((StateEvent.all.equals(event) || StateEvent.onStateEntry.equals(event)) && matchState(flow.getCurrentState())) {
			if(log.isDebugEnabled()) {
				log.debug("onStateEntry: " + toString());
			}
			if(log.isTraceEnabled()) {
				log.trace(flow.toString());
			}
			onStateEntryFiltered(flow);
		} 
	}

	@Override
	public void onStateLeaving(IFlowCarter<T> flow) {
		if((StateEvent.all.equals(event) || StateEvent.onStateLeaving.equals(event)) && matchState(flow.getCurrentState())) {
			if(log.isDebugEnabled()) {
				log.debug("onStateLeaving: " + toString());
			}
			if(log.isTraceEnabled()) {
				log.trace(flow.toString());
			}
			onStateLeavingFiltered(flow);
		} 
	}
	
	public boolean matchState(IFlowState flowState) {
		return matchStateName(flowState.getStateName()) && matchStateType(flowState);
	}
	
	public boolean matchStateName(String stateName) {
		return this.stateNameRegex == null || stateName.matches(this.stateNameRegex);
	}

	public boolean matchStateType(IFlowState implementingObject) {
		return this.type == IFlowState.class || this.type.isInstance(implementingObject);
	}

	public StateEvent getEvent() {
		return event;
	}

	public void setEvent(StateEvent event) {
		this.event = event;
	}

	public String getStateNameRegex() {
		return stateNameRegex;
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNameRegex = stateNameRegex;
	}

	public Class<? extends IFlowState> getType() {
		return type;
	}

	public void setType(Class<? extends IFlowState> type) {
		this.type = type;
	}

	abstract protected void onStateEntryFiltered(IFlowCarter<T> flow);

	abstract protected void onStateLeavingFiltered(IFlowCarter<T> flow);

	@Override
	protected void finalize() throws Throwable {
		event = null;
		stateNameRegex = null;
		type = null;
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(": event=");
		sb.append(event);
		sb.append(", stateNameRegex=");
		sb.append(stateNameRegex);
		sb.append(", type=");
		sb.append(type);
		sb.append(", priority=");
		sb.append(priority);
		return sb.toString();
	}
	
	

}
