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
package cz.wicketstuff.boss.flow.builder.xml;

import cz.wicketstuff.boss.flow.builder.xml.jaxb.StateType;
import cz.wicketstuff.boss.flow.model.basic.FlowState;

/**
 * A capsule of a state descriptor containing state object
 * and its XML descriptor.
 * 
 * @author Martin Strejc
 *
 */
public class StateCapsule {
	
	private StateType state;
	private FlowState flowState;

	public StateCapsule() {
	}
	
	public StateCapsule(StateType state) {
		this.state = state;
	}
	
	public StateCapsule(FlowState flowState) {
		this.flowState = flowState;
	}
	
	public StateCapsule(StateType state, FlowState flowState) {
		this.state = state;
		this.flowState = flowState;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public FlowState getFlowState() {
		return flowState;
	}

	public void setFlowState(FlowState flowState) {
		this.flowState = flowState;
	}

	@Override
	protected void finalize() throws Throwable {
		state = null;
		flowState = null;
		super.finalize();
	}
	
}
