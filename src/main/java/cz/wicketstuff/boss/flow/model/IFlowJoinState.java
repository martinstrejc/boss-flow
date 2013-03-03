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
package cz.wicketstuff.boss.flow.model;

/**
 * The interface defines a dummy state that just pass trough
 * using the next transition to redirect to another state.
 * 
 * Transition can also reference the target state directly,
 * however it is sometimes useful to have an state beside two states
 * because of event listeners or if a refactoring of flow is expected.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowJoinState extends IFlowVirtualState {
	
	/**
	 * Return the transition that is going to be processed.
	 * 
	 * @return transition to process
	 */
	public IFlowTransition getNextTransition();

}
