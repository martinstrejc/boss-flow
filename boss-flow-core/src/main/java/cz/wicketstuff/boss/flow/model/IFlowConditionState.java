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
 * The condition state is an equivalent to 'if' in java language.
 * When the expression is {@code true}, then the 'thenTransition' is going to be processed,
 * otherwise the 'elseTransition' is going to be processed.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowConditionState extends IFlowVirtualState {

	/**
	 * Return the expression name that the processor try
	 * to find and it is used to decide which transition will be processed.
	 * 
	 * @return expression name
	 */
	public String getConditionExpression();
	
	/**
	 * Return the transition that will be processed when condition is {@code true}
	 * 
	 * @return transition processed if condition is {@code true}
	 */
	public IFlowTransition getThenTransition();
	
	/**
	 * Return the transition that will be processed when condition is 'false'
	 * 
	 * @return transition processed if condition is {@code false}
	 */
	public IFlowTransition getElseTransition();
	
}
