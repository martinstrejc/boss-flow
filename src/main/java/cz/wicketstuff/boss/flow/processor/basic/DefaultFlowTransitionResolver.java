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
package cz.wicketstuff.boss.flow.processor.basic;

import java.io.Serializable;
import java.util.Iterator;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionResolver;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;

public class DefaultFlowTransitionResolver<T extends Serializable> implements IFlowTransitionResolver<T>, Serializable {

	private static final long serialVersionUID = 1L;

	
	public DefaultFlowTransitionResolver() {
	}

	@Override
	public IFlowTransition resolveTransition(IFlowCarter<T> flow, String transitionName)
			throws NoSuchTransitionException {
		if(transitionName == null) {
			throw new NoSuchTransitionException("Transition 'null' does not exist.");
		}
		for(Iterator<IFlowTransition> it = flow.getCurrentState().getAvailableTransitions(); it.hasNext();) {
			IFlowTransition t = it.next();
			if(transitionName.equals(it.next().getTransitionName())) {
				return t;
			}
		}
		throw new NoSuchTransitionException("Transition name='" + transitionName + "' does not exist.");
	}


}
