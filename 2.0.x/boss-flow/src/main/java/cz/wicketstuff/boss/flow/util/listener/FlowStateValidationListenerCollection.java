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
package cz.wicketstuff.boss.flow.util.listener;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.processor.FlowValidationListenerException;
import cz.wicketstuff.boss.flow.processor.IFlowStateValidationListener;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.FlowListenerCollection;

public class FlowStateValidationListenerCollection<T extends Serializable> extends
		FlowListenerCollection<IFlowStateValidationListener<T>, FlowValidationListenerException> implements
		IFlowStateValidationListener<T> {

	private static final long serialVersionUID = 1L;
	
	private int priority = 0;
	
	public FlowStateValidationListenerCollection() {
		super();
	}
	
	public FlowStateValidationListenerCollection(int priority) {
		super();
		this.priority = priority;
	}

	@Override
	public void onStateValid(final IFlowCarter<T> flow) throws FlowValidationListenerException {
		notify(new INotifier<IFlowStateValidationListener<T>, FlowValidationListenerException>() {
			@Override
			public void notify(IFlowStateValidationListener<T> listener) throws FlowValidationListenerException {
				listener.onStateValid(flow);
			}
		});
	}

	@Override
	public void onStateInvalid(final IFlowCarter<T> flow) throws FlowValidationListenerException {
		notify(new INotifier<IFlowStateValidationListener<T>, FlowValidationListenerException>() {
			@Override
			public void notify(IFlowStateValidationListener<T> listener) throws FlowValidationListenerException {
				listener.onStateInvalid(flow);
			}
		});
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

}