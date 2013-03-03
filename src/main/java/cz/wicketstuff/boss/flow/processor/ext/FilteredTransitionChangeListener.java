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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent.TransitionEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionChangeListener;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

public abstract class FilteredTransitionChangeListener<T extends Serializable> implements IFlowTransitionChangeListener<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(FilteredTransitionChangeListener.class);

	private TransitionEvent event;
	
	private String transitionNameRegex;

	private int priority;

	public FilteredTransitionChangeListener() {
	}

	
	public FilteredTransitionChangeListener(TransitionEvent event, String transitionNameRegex, int priority) {
		this.event = event;
		this.transitionNameRegex = transitionNameRegex;
		this.priority = priority;
	}


	@Override
	public void onTransitionStart(IFlowCarter<T> flow) {
		if((TransitionEvent.all.equals(event) || TransitionEvent.onTransitionStart.equals(event)) && matchTransition(flow.getNextTransition())) {
			if(log.isDebugEnabled()) {
				log.debug("onTransitionStart: " + toString());
			}
			if(log.isTraceEnabled()) {
				log.trace(flow.toString());
			}
			onTransitionStartFiltered(flow);
		} 
		
	}

	@Override
	public void onTransitionFinished(IFlowCarter<T> flow) {
		if((TransitionEvent.all.equals(event) || TransitionEvent.onTransitionFinished.equals(event)) && matchTransition(flow.getPreviousTransition())) {
			if(log.isDebugEnabled()) {
				log.debug("onTransitionFinished: " + toString());
			}
			if(log.isTraceEnabled()) {
				log.trace(flow.toString());
			}
			onTransitionFinishedFiltered(flow);
		} 		
	}

	public boolean matchTransition(IFlowTransition transition) {
		return this.transitionNameRegex == null || transition.getTransitionName().matches(this.transitionNameRegex);
	}

	public TransitionEvent getEvent() {
		return event;
	}


	public void setEvent(TransitionEvent event) {
		this.event = event;
	}


	public String getTransitionNameRegex() {
		return transitionNameRegex;
	}

	public void setTransitionNameRegex(String transitionNameRegex) {
		this.transitionNameRegex = transitionNameRegex;
	}

	abstract protected void onTransitionStartFiltered(IFlowCarter<T> flow);

	abstract protected void onTransitionFinishedFiltered(IFlowCarter<T> flow);

	@Override
	protected void finalize() throws Throwable {
		event = null;
		transitionNameRegex = null;
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
		sb.append(", transitionNameRegex=");
		sb.append(transitionNameRegex);
		sb.append(", priority=");
		sb.append(priority);
		return sb.toString();
	}

}
