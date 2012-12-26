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
package com.etnetera.boss.flow.processor.ext;

import java.io.Serializable;
import java.util.List;

import com.etnetera.boss.flow.util.listener.FlowStateChangeListenerCollection;
import com.etnetera.boss.flow.util.listener.FlowStateValidationListenerCollection;
import com.etnetera.boss.flow.util.listener.FlowTransitionChangeListenerCollection;
import com.etnetera.boss.flow.util.processor.FlowConditionStateProcessorCollection;
import com.etnetera.boss.flow.util.processor.FlowSwitchStateProcessorCollection;

public class AnnotationListFlowFactory<T extends Serializable> extends AnnotationFlowFactory<T> {

	private List<Object> annotedBeans;
	
	public AnnotationListFlowFactory() {
	}

	public AnnotationListFlowFactory(List<Object> annotedBeans) {
		this.annotedBeans = annotedBeans;
	}
	
	public FlowStateChangeListenerCollection<T> getStateChangeListeners() throws FlowAnnotationException {
		FlowStateChangeListenerCollection<T> collection = newFlowStateChangeListenerCollection();
		for(Object o : annotedBeans) {
			getStateChangeListeners(o, collection);
		}
		return collection;
	}

	public FlowStateValidationListenerCollection<T> getStateValidationListeners() throws FlowAnnotationException {
		FlowStateValidationListenerCollection<T> collection = newFlowStateValidationListenerCollection();
		for(Object o : annotedBeans) {
			getStateValidationListeners(o, collection);
		}
		return collection;
	}
	
	public FlowTransitionChangeListenerCollection<T> getTransitionChangeListeners() throws FlowAnnotationException {
		FlowTransitionChangeListenerCollection<T> collection = newFlowTransitionChangeListenerCollection();
		for(Object o : annotedBeans) {
			getTransitionChangeListeners(o, collection);
		}
		return collection;
	}

	public FlowConditionStateProcessorCollection<T> getFlowConditionProcessors() throws FlowAnnotationException {
		FlowConditionStateProcessorCollection<T> collection = newFlowConditionStateCollection();
		for(Object o : annotedBeans) {
			getFlowConditionProcessors(o, collection);
		}
		return collection;
	}

	public FlowSwitchStateProcessorCollection<T> getFlowSwitchProcessors() throws FlowAnnotationException {
		FlowSwitchStateProcessorCollection<T> collection = newFlowSwitchStateProcessorCollection();
		for(Object o : annotedBeans) {
			getFlowSwitchProcessors(o, collection);
		}
		return collection;
	}

	public List<Object> getAnnotedBeans() {
		return annotedBeans;
	}

	public void setAnnotedBeans(List<Object> annotedBeans) {
		this.annotedBeans = annotedBeans;
	}

	@Override
	protected void finalize() throws Throwable {
		if(annotedBeans != null) {
			annotedBeans.clear();
			annotedBeans = null;
		}
		super.finalize();
	}

}
