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
 * Transition resolver.
 * 
 * @author Martin Strejc
 *
 * @param <T>  type of flow payload
 */
public interface IFlowTransitionResolver<T extends Serializable> {
	
	/**
	 * Resolve transition by name
	 * 
	 * @param flow
	 * @param transitionName
	 * @return
	 * @throws NoSuchTransitionException
	 */
	IFlowTransition resolveTransition(IFlowCarter<T> flow, String transitionName) throws NoSuchTransitionException;
	
	/**
	 * Resolve next transition by name or by criteria. Name needn't be applied.
	 * 
	 * @param flow
	 * @return
	 * @throws NoSuchTransitionException
	 * @throws UnsupportedStateOperationException
	 */
	IFlowTransition resolveNextTransition(IFlowCarter<T> flow) throws NoSuchTransitionException, UnsupportedStateOperationException;
	
	/**
	 * Resolve previous transition by name or by criteria. Name needn't be applied.
	 * 
	 * @param flow
	 * @return
	 * @throws NoSuchTransitionException
	 * @throws UnsupportedStateOperationException
	 */
	IFlowTransition resolvePreviousTransition(IFlowCarter<T> flow) throws NoSuchTransitionException, UnsupportedStateOperationException;

}
