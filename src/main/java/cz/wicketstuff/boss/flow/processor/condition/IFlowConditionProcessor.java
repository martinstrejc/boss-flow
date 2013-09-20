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
package cz.wicketstuff.boss.flow.processor.condition;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.model.IFlowCarter;

/**
 * Processor of Condition State
 * 
 * @author Martin Strejc
 *
 * @param <T> type of flow payload
 */
public interface IFlowConditionProcessor<T extends Serializable> {
	
	/**
	 * Evaluate the expression of condition state and return its boolean result.
	 *   
	 * @param conditionExpression
	 * @param flow
	 * @return result of evaluating boolean expression
	 * @throws FlowIfConditionException
	 */
	public boolean ifCondition(String conditionExpression, IFlowCarter<T> flow) throws FlowIfConditionException;

}
