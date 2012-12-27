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
