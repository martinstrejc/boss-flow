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
import java.util.List;

import cz.wicketstuff.boss.flow.util.listener.FlowStateChangeListenerCollection;
import cz.wicketstuff.boss.flow.util.listener.FlowStateValidationListenerCollection;
import cz.wicketstuff.boss.flow.util.listener.FlowTransitionChangeListenerCollection;
import cz.wicketstuff.boss.flow.util.processor.FlowConditionStateProcessorCollection;
import cz.wicketstuff.boss.flow.util.processor.FlowSwitchStateProcessorCollection;

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
