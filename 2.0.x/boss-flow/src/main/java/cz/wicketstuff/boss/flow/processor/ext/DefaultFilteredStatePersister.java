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
import java.util.regex.Pattern;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.FlowPersistingException;
import cz.wicketstuff.boss.flow.processor.FlowRestoringException;

/**
 * Dummy implementation of filtered state persister
 * 
 * @author Martin Strejc
 *
 */
public class DefaultFilteredStatePersister<T extends Serializable> extends FilteredStatePersister<T> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public DefaultFilteredStatePersister() {
		super();
	}

	/**
	 * @param stateNamePattern
	 * @param categoryNamePattern
	 * @param type
	 * @param priority
	 */
	public DefaultFilteredStatePersister(
			Pattern stateNamePattern, Pattern categoryNamePattern,
			Class<? extends IFlowState> type, int priority) {
		super(stateNamePattern, categoryNamePattern, type, priority);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param stateNameRegex
	 * @param categoryNameRegex
	 * @param type
	 * @param priority
	 */
	public DefaultFilteredStatePersister(
			String stateNameRegex, String categoryNameRegex,
			Class<? extends IFlowState> type, int priority) {
		super(stateNameRegex, categoryNameRegex, type, priority);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean persistFlowStateFiltered(IFlowCarter<T> flow)
			throws FlowPersistingException {
		return false;
	}

	@Override
	protected IFlowCarter<T> restoreFlowStateFiltered(long flowProcessId)
			throws FlowRestoringException {
		return null;
	}

}
