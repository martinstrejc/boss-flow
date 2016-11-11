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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.FlowPersistingException;
import cz.wicketstuff.boss.flow.processor.FlowRestoringException;
import cz.wicketstuff.boss.flow.processor.IFlowStatePersister;
import cz.wicketstuff.boss.flow.util.Comparators;
import cz.wicketstuff.boss.flow.util.FlowMatcherHelper;
import cz.wicketstuff.boss.flow.util.FlowStringUtils;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

public abstract class FilteredStatePersister<T extends Serializable> implements IFlowStatePersister<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FilteredStatePersister.class);
	
	private Pattern stateNamePattern;
	
	private Pattern categoryNamePattern;
	
	private Class<? extends IFlowState> type;
	
	private int priority;

	public FilteredStatePersister() {
	}

	
	public FilteredStatePersister(String stateNameRegex,
			String categoryNameRegex, 
			Class<? extends IFlowState> type, 
			int priority) {
		this(
				FlowMatcherHelper.strigToPattern(stateNameRegex), 
				FlowMatcherHelper.strigToPattern(categoryNameRegex), 
				type, 
				priority);
	}

	public FilteredStatePersister(Pattern stateNamePattern, Pattern categoryNamePattern,
			Class<? extends IFlowState> type, int priority) {
		this.stateNamePattern = stateNamePattern;
		this.categoryNamePattern = categoryNamePattern;
		this.type = type;
		this.priority = priority;
	}
	
	@Override
	public boolean persistFlowState(IFlowCarter<T> flow)
			throws FlowPersistingException {
		if(matchState(flow.getCurrentState())) {
			if(log.isDebugEnabled()) {
				log.debug("persistFlowState: " + toString());
			}
			if(log.isTraceEnabled()) {
				log.trace(flow.toString());
			}
			return persistFlowStateFiltered(flow);
		}
		return false;
	}
	
	@Override
	public IFlowCarter<T> restoreFlowState(long flowProcessId)
			throws FlowRestoringException {
		return restoreFlowStateFiltered(flowProcessId);
	}
	
	public boolean matchState(IFlowState flowState) {
		return matchStateName(flowState.getStateName()) && matchStateType(flowState);
	}
	
	public boolean matchStateName(String stateName) {
		return this.stateNamePattern == null || stateNamePattern.matcher(stateName).matches();
	}

	public boolean matchStateType(IFlowState implementingObject) {
		return this.type == IFlowState.class || this.type.isInstance(implementingObject);
	}

	public String getStateNameRegex() {
		return FlowStringUtils.safeToString(stateNamePattern);
	}

	public void setStateNameRegex(String stateNameRegex) {
		this.stateNamePattern = FlowMatcherHelper.strigToPattern(stateNameRegex);
	}

	public Class<? extends IFlowState> getType() {
		return type;
	}

	public void setType(Class<? extends IFlowState> type) {
		this.type = type;
	}

	/**
	 * @return the stateNamePattern
	 */
	public Pattern getStateNamePattern() {
		return stateNamePattern;
	}


	/**
	 * @param stateNamePattern the stateNamePattern to set
	 */
	public void setStateNamePattern(Pattern stateNamePattern) {
		this.stateNamePattern = stateNamePattern;
	}


	abstract protected boolean persistFlowStateFiltered(IFlowCarter<T> flow)
			throws FlowPersistingException;
	
	abstract protected IFlowCarter<T> restoreFlowStateFiltered(long flowProcessId)
			throws FlowRestoringException;

	@Override
	protected void finalize() throws Throwable {
		stateNamePattern = null;
		categoryNamePattern = null;
		type = null;
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

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**
	 * @return the categoryNamePattern
	 */
	public Pattern getCategoryNamePattern() {
		return categoryNamePattern;
	}


	/**
	 * @param categoryNamePattern the categoryNamePattern to set
	 */
	public void setCategoryNamePattern(Pattern categoryNamePattern) {
		this.categoryNamePattern = categoryNamePattern;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(": stateNamePattern=");
		sb.append(FlowStringUtils.safeToString(stateNamePattern));
		sb.append(", categoryNamePattern=");
		sb.append(FlowStringUtils.safeToString(categoryNamePattern));
		sb.append(", type=");
		sb.append(type);
		sb.append(", priority=");
		sb.append(priority);
		return sb.toString();
	}
	
	

}
