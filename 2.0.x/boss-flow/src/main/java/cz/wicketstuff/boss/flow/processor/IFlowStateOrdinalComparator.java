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

import java.util.Comparator;

import cz.wicketstuff.boss.flow.model.IFlowState;

/**
 * Comparator that compares two flow states by their oridnal value.
 *  
 * @author Martin Strejc
 *
 */
public interface IFlowStateOrdinalComparator {

	/**
	 * Comparator derived from {@link Comparator}
	 * 
	 * @param state1
	 * @param state2
	 * @return 0, negative or possitive as defined for {@link Comparator} 
	 */
	public int compareStatesOrdinality(IFlowState state1, IFlowState state2);

}
