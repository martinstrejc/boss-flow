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

import cz.wicketstuff.boss.flow.builder.xml.jaxb.TransitionType;
import cz.wicketstuff.boss.flow.model.basic.FlowTransition;

/**
 * A capsule of a transition descriptor containing transition object
 * and its XML descriptor.
 * 
 * @author Martin Strejc
 *
 */
public class TransitionCapsule {

	private TransitionType transition;
	private FlowTransition flowTransition;
	
	public TransitionCapsule() {
	}

	public TransitionCapsule(TransitionType transition) {
		this.transition = transition;
	}

	public TransitionCapsule(FlowTransition flowTransition) {
		this.flowTransition = flowTransition;
	}

	public TransitionCapsule(TransitionType transition,
			FlowTransition flowTransition) {
		this.transition = transition;
		this.flowTransition = flowTransition;
	}

	public TransitionType getTransition() {
		return transition;
	}

	public void setTransition(TransitionType transition) {
		this.transition = transition;
	}

	public FlowTransition getFlowTransition() {
		return flowTransition;
	}

	public void setFlowTransition(FlowTransition flowTransition) {
		this.flowTransition = flowTransition;
	}

	@Override
	protected void finalize() throws Throwable {
		transition = null;
		flowTransition = null;
		super.finalize();
	}
	
}
