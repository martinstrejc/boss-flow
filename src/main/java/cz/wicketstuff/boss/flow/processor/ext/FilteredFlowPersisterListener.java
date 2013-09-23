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

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.processor.FlowPersisterListenerException;
import cz.wicketstuff.boss.flow.processor.IFlowPersisterListener;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

public abstract class FilteredFlowPersisterListener<T extends Serializable> implements IFlowPersisterListener<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredFlowPersisterListener.class);
	
	private int priority;

	public FilteredFlowPersisterListener() {
	}

	
	public FilteredFlowPersisterListener(int priority) {
		this.priority = priority;
	}


	@Override
	public void onFlowBeforePersisted(IFlowCarter<T> flow)
			throws FlowPersisterListenerException {
		if(log.isDebugEnabled()) {
			log.debug("onFlowBeforePersisted: " + toString());
		}
		if(log.isTraceEnabled()) {
			log.trace(flow.toString());
		}
		onFlowBeforePersistedFiltered(flow);
	}


	@Override
	public void onFlowPersisted(IFlowCarter<T> flow)
			throws FlowPersisterListenerException {
		if(log.isDebugEnabled()) {
			log.debug("onFlowPersisted: " + toString());
		}
		if(log.isTraceEnabled()) {
			log.trace(flow.toString());
		}
		onFlowPersistedFiltered(flow);		
	}


	@Override
	public void onFlowRestored(IFlowCarter<T> flow)
			throws FlowPersisterListenerException {
		if(log.isDebugEnabled()) {
			log.debug("onFlowRestored: " + toString());
		}
		if(log.isTraceEnabled()) {
			log.trace(flow.toString());
		}
		onFlowRestoredFiltered(flow);
		
	}
		
	abstract protected void onFlowBeforePersistedFiltered(IFlowCarter<T> flow)
			throws FlowPersisterListenerException;


	abstract protected void onFlowPersistedFiltered(IFlowCarter<T> flow)
			throws FlowPersisterListenerException;


	abstract protected void onFlowRestoredFiltered(IFlowCarter<T> flow)
			throws FlowPersisterListenerException;
	
	
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
		sb.append("priority=");
		sb.append(priority);
		return sb.toString();
	}


}
