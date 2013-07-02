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
package cz.wicketstuff.boss.flow.processor.ext;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.annotation.FlowStateEvent.StateEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.IFlowStateChangeListener;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

public abstract class FilteredStateChangeListener<T extends Serializable> implements IFlowStateChangeListener<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStateChangeListener.class);
	
	private StateEvent event;
	
	private Pattern stateNamePattern;
	
	private Pattern categoryNamePattern;
	
	private Class<? extends IFlowState> type;
	
	private int priority;

	public FilteredStateChangeListener() {
	}

	
	public FilteredStateChangeListener(StateEvent event, String stateNameRegex,
			String categoryNameRegex, 
			Class<? extends IFlowState> type, 
			int priority) {
		this(event, Pattern.compile(stateNameRegex), Pattern.compile(categoryNameRegex), type, priority);
	}

	public FilteredStateChangeListener(StateEvent event, Pattern stateNamePattern, Pattern categoryNamePattern,
			Class<? extends IFlowState> type, int priority) {
		this.event = event;
		this.stateNamePattern = stateNamePattern;
		this.categoryNamePattern = categoryNamePattern;
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
		return this.stateNamePattern == null || stateNamePattern.matcher(stateName).matches();
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
		return stateNamePattern.toString();
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNamePattern = Pattern.compile(stateNameRegex);
	}

	public Class<? extends IFlowState> getType() {
		return type;
	}

	public void setType(Class<? extends IFlowState> type) {
		this.type = type;
	}

	/**
	 * @return the stateNamePattern
	 */
	public Pattern getStateNamePattern() {
		return stateNamePattern;
	}


	/**
	 * @param stateNamePattern the stateNamePattern to set
	 */
	public void setStateNamePattern(Pattern stateNamePattern) {
		this.stateNamePattern = stateNamePattern;
	}


	abstract protected void onStateEntryFiltered(IFlowCarter<T> flow);

	abstract protected void onStateLeavingFiltered(IFlowCarter<T> flow);

	@Override
	protected void finalize() throws Throwable {
		event = null;
		stateNamePattern = null;
		categoryNamePattern = null;
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
		sb.append(", stateNamePattern=");
		sb.append(stateNamePattern.toString());
		sb.append(", categoryNamePattern=");
		sb.append(categoryNamePattern.toString());
		sb.append(", type=");
		sb.append(type);
		sb.append(", priority=");
		sb.append(priority);
		return sb.toString();
	}
	
	

}
