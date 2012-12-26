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
package com.etnetera.boss.flow.util.processor;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.processor.condition.CannotProcessConditionException;
import com.etnetera.boss.flow.processor.condition.IFlowConditionProcessor;
import com.etnetera.boss.flow.processor.condition.IFlowMatchedConditionProcessor;

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
			throws CannotProcessConditionException {
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
		throw new CannotProcessConditionException("Condition hasn't been matched and processed. Probably it is an uknown expression.");
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
