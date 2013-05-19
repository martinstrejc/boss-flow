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

import cz.wicketstuff.boss.flow.annotation.FlowEvents.FlowEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent.StateEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.processor.IFlowListener;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

public abstract class FilteredFlowListener<T extends Serializable> implements IFlowListener<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredFlowListener.class);
	
	private FlowEvent event;
	
	private int priority;

	public FilteredFlowListener() {
	}

	
	public FilteredFlowListener(FlowEvent event, int priority) {
		this.event = event;
		this.priority = priority;
	}


	@Override
	public void onFlowInitialized(IFlowCarter<T> flow) {
		if(StateEvent.all.equals(event) || StateEvent.onStateEntry.equals(event)) {
			if(log.isDebugEnabled()) {
				log.debug("onFlowInitialized: " + toString());
			}
			if(log.isTraceEnabled()) {
				log.trace(flow.toString());
			}
			onFlowInitializedFiltered(flow);
		} 
	}

	@Override
	public void onFlowFinished(IFlowCarter<T> flow) {
		if(StateEvent.all.equals(event) || StateEvent.onStateLeaving.equals(event)) {
			if(log.isDebugEnabled()) {
				log.debug("onFlowFinished: " + toString());
			}
			if(log.isTraceEnabled()) {
				log.trace(flow.toString());
			}
			onFlowFinishedFiltered(flow);
		} 
	}
	
	public FlowEvent getEvent() {
		return event;
	}

	public void setEvent(FlowEvent event) {
		this.event = event;
	}

	abstract protected void onFlowInitializedFiltered(IFlowCarter<T> flow);

	abstract protected void onFlowFinishedFiltered(IFlowCarter<T> flow);

	@Override
	protected void finalize() throws Throwable {
		event = null;
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
		sb.append(", priority=");
		sb.append(priority);
		return sb.toString();
	}
	
	

}
