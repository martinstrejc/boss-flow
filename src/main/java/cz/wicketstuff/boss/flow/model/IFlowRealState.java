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
 * The basic interface of real states.
 * A real state is a state that the flow can
 * stand by. Compare to {@link IFlowVirtualState}
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowRealState extends IFlowState {

	/**
	 * Return a transition that is used as default next transition
	 * 
	 * @return default next transition
	 */
	public IFlowTransition getDefaultNextTransition();
	
	/**
	 * Return a transition that is used as default previous transition
	 * 
	 * @return default previous transition
	 */
	public IFlowTransition getDefaultPreviousTransition();
	

}
