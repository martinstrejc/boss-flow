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
package cz.wicketstuff.boss.flow.util.processor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.processor.FlowPersistingException;
import cz.wicketstuff.boss.flow.processor.FlowRestoringException;
import cz.wicketstuff.boss.flow.processor.IFlowStatePersister;
import cz.wicketstuff.boss.flow.processor.condition.IFlowMatchedConditionProcessor;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

public class FlowStatePersisterCollection<T extends Serializable> 
			implements IFlowStatePersister<T>, Serializable, Iterable<IFlowStatePersister<T>>  {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(FlowStatePersisterCollection.class);
	
	private int priority = 0;
	
	private final List<IFlowStatePersister<T>> processors;

	
	public FlowStatePersisterCollection() {
		this(null);
	}
	
	public FlowStatePersisterCollection(
			List<IFlowStatePersister<T>> processors) {
		this.processors = createList(processors);
	}

	@Override
	public boolean persistFlowState(IFlowCarter<T> flow)
			throws FlowPersistingException {
		for(IFlowStatePersister<T> processor : processors) {
			if(processor.persistFlowState(flow)) {
				log.debug("Flow has been succesfully persisted");
				return true;
			}
		}
		log.debug("Flow has not been persisted");
		return false;
	}

	@Override
	public IFlowCarter<T> restoreFlowState(long flowProcessId) throws FlowRestoringException {
		for(IFlowStatePersister<T> processor : processors) {
			IFlowCarter<T> flow = processor.restoreFlowState(flowProcessId);
			if(flow != null) {
				log.debug("Flow has been restored");
				return flow;
			}
		}
		log.debug("Flow has not been restored");
		return null;
	}
	
	public List<IFlowStatePersister<T>> createList(List<IFlowStatePersister<T>> processors) {
		if(processors == null) {
			return new CopyOnWriteArrayList<IFlowStatePersister<T>>();
		} else {
			return new CopyOnWriteArrayList<IFlowStatePersister<T>>(processors);			
		}		
	}

	public boolean add(IFlowStatePersister<T> processor) {
		return processors.add(processor);
	}

	public boolean remove(IFlowMatchedConditionProcessor<T> processor) {
		return processors.remove(processor);
	}

	@Override
	public Iterator<IFlowStatePersister<T>> iterator() {
		return processors.iterator();
	}

	@Override
	protected void finalize() throws Throwable {
		if(processors != null) {
			processors.clear();
		}
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

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void sort() {
		List<IFlowStatePersister<T>> list = new ArrayList<IFlowStatePersister<T>>(processors);
		Collections.sort(list);
		processors.clear();
		processors.addAll((List<IFlowStatePersister<T>>)list);
	}	
	
	public void sort(Comparator<IFlowStatePersister<T>> comparator) {
		ArrayList<IFlowStatePersister<T>> list = new ArrayList<IFlowStatePersister<T>>(processors);
		Collections.sort(list, comparator);
		processors.clear();
		processors.addAll(list);
	}

}
