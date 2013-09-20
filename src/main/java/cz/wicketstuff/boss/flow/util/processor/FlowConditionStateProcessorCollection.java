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
import cz.wicketstuff.boss.flow.processor.condition.FlowIfConditionException;
import cz.wicketstuff.boss.flow.processor.condition.IFlowConditionProcessor;
import cz.wicketstuff.boss.flow.processor.condition.IFlowMatchedConditionProcessor;

public class FlowConditionStateProcessorCollection<T extends Serializable> 
			implements IFlowConditionProcessor<T>, Serializable, Iterable<IFlowMatchedConditionProcessor<T>>  {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(FlowConditionStateProcessorCollection.class);
	
	private final List<IFlowMatchedConditionProcessor<T>> processors;

	public FlowConditionStateProcessorCollection() {
		this(null);
	}
	
	public FlowConditionStateProcessorCollection(
			List<IFlowMatchedConditionProcessor<T>> processors) {
		this.processors = createList(processors);
	}
	
	@Override
	public boolean ifCondition(String conditionExpression, IFlowCarter<T> flow)
			throws FlowIfConditionException {
		if(log.isDebugEnabled()) {
			log.debug("ifCondition conditionExpression: " + conditionExpression);
		}
		if(log.isTraceEnabled()) {
			log.trace("flow: " + flow.toString());
		}
		for(IFlowMatchedConditionProcessor<T> processor : processors) {
			if(processor.match(conditionExpression, flow)) {
				return processor.ifCondition(conditionExpression, flow);
			}
		}
		throw new FlowIfConditionException("Condition hasn't been matched and processed. Probably it is an uknown expression.");
	}
	
	public List<IFlowMatchedConditionProcessor<T>> createList(List<IFlowMatchedConditionProcessor<T>> processors) {
		if(processors == null) {
			return new CopyOnWriteArrayList<IFlowMatchedConditionProcessor<T>>();
		} else {
			return new CopyOnWriteArrayList<IFlowMatchedConditionProcessor<T>>(processors);			
		}		
	}

	public boolean add(IFlowMatchedConditionProcessor<T> processor) {
		return processors.add(processor);
	}

	public boolean remove(IFlowMatchedConditionProcessor<T> processor) {
		return processors.remove(processor);
	}

	@Override
	public Iterator<IFlowMatchedConditionProcessor<T>> iterator() {
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
