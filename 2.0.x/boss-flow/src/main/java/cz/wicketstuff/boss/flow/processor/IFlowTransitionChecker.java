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
import cz.wicketstuff.boss.flow.model.IFlowTransition;

/**
 * Checking the transition validity.
 * 
 * @author Martin Strejc
 *
 * @param <T> type of flow payload
 */
public interface IFlowTransitionChecker<T extends Serializable> {	
		
	/**
	 * Checks if transition is valid for the flow and for the current state.
	 * You can call this method to prevent exception on invoking an invalid transition.
	 * 
	 * @param flow
	 * @param nextTransition
	 * @return
	 */
	boolean checkTransition(IFlowCarter<T> flow, IFlowTransition nextTransition);
	
}