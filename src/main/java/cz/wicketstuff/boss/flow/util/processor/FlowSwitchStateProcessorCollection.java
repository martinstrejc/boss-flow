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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.processor.condition.IFlowMatchedSwitchProcessor;
import cz.wicketstuff.boss.flow.processor.condition.IFlowSwitchProcessor;

public class FlowSwitchStateProcessorCollection<T extends Serializable> 
			implements IFlowSwitchProcessor<T>, Serializable, Iterable<IFlowMatchedSwitchProcessor<T>>  {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(FlowSwitchStateProcessorCollection.class);
	
	private final List<IFlowMatchedSwitchProcessor<T>> processors;

	public FlowSwitchStateProcessorCollection() {
		this(null);
	}
	
	public FlowSwitchStateProcessorCollection(
			List<IFlowMatchedSwitchProcessor<T>> processors) {
		this.processors = createList(processors);
	}
	
	@Override
	public String resolveSwitchExpression(IFlowCarter<T> flow,
			String switchExpression) {
		if(log.isDebugEnabled()) {
			log.debug("resolveSwitchExpression: " + switchExpression);
		}
		if(log.isTraceEnabled()) {
			log.trace("flow: " + flow.toString());
		}		
		for(IFlowMatchedSwitchProcessor<T> processor : processors) {
			if(processor.match(switchExpression, flow)) {
				return processor.resolveSwitchExpression(flow, switchExpression);
			}
		}
		return null;
	}

	public List<IFlowMatchedSwitchProcessor<T>> createList(List<IFlowMatchedSwitchProcessor<T>> processors) {
		if(processors == null) {
			return new CopyOnWriteArrayList<IFlowMatchedSwitchProcessor<T>>();
		} else {
			return new CopyOnWriteArrayList<IFlowMatchedSwitchProcessor<T>>(processors);			
		}		
	}

	public boolean add(IFlowMatchedSwitchProcessor<T> processor) {
		return processors.add(processor);
	}

	public boolean remove(IFlowMatchedSwitchProcessor<T> processor) {
		return processors.remove(processor);
	}

	@Override
	public Iterator<IFlowMatchedSwitchProcessor<T>> iterator() {
		return processors.iterator();
	}

	@Override
	protected void finalize() throws Throwable {
		if(processors != null) {
			processors.clear();
		}
		super.finalize();
	}

}
