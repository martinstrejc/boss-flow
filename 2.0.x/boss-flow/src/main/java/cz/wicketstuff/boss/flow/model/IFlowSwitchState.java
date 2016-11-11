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

import java.util.Iterator;

/**
 * The switch state is an equivalent to 'switch' in java language.
 * When the result of expression equals one of {@link #getSwitchCases()} 
 * the associated transition is going to be processed,
 * otherwise nothing match 'defaultTransition' is going to be processed.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowSwitchState extends IFlowVirtualState {
	
	/**
	 * Return the expression name that the processor try
	 * to find and it is used to decide which transition will be processed.
	 * 
	 * @return expression name
	 */
	public String getSwitchExpression();

	/**
	 * Return iterator over possible switch cases.
	 * 
	 * @return switch cases iterator
	 */
	public Iterator<ISwitchCase> getSwitchCases();
	
	/**
	 * Return the default transition used when none of {@link #getSwitchCases()}
	 * matches the result of the expression.
	 * 
	 * @return default transition
	 */
	public IFlowTransition getDefaultTransition();
	
}
