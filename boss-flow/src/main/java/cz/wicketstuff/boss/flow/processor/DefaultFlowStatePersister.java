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
package cz.wicketstuff.boss.flow.processor;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

/**
 * Dummy implementation of {@link IFlowStatePersister}
 * 
 * @author Martin Strejc
 *
 */
public class DefaultFlowStatePersister<T extends Serializable> implements IFlowStatePersister<T> {

	private static final long serialVersionUID = 1L;

	private int priority;
	
	public DefaultFlowStatePersister() {
		this(0);
	}

	public DefaultFlowStatePersister(int priority) {
		this.priority = priority;
	}

	@Override
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(IPriority o) {
		return Comparators.compareInts(priority, o.getPriority());
		
	}

	@Override
	public boolean persistFlowState(IFlowCarter<T> flow)
			throws FlowPersistingException {
		return false;
	}

	@Override
	public IFlowCarter<T> restoreFlowState(long flowProcessId)
			throws FlowRestoringException {
		return null;
	}
	
	

}
