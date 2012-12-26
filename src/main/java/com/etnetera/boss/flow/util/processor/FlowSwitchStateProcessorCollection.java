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
import com.etnetera.boss.flow.processor.condition.IFlowMatchedSwitchProcessor;
import com.etnetera.boss.flow.processor.condition.IFlowSwitchProcessor;

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
