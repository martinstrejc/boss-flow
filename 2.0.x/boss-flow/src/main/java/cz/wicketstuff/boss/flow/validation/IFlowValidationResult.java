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
package cz.wicketstuff.boss.flow.validation;

import java.util.Iterator;

/**
 * Validation result API interface
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowValidationResult {

	/**
	 * Return the key of validation result.
	 * It can be used in a localizer or to identify the validation problem.
	 * 
	 * @return
	 */
	public String getItemKey();
	
	/**
	 * Return true if this result means that state is valid.
	 * 
	 * @return true for valid state
	 */
	public boolean isValid();
	
	/**
	 * Return all messages that has been passed during this validation
	 * related to the key {@link #getItemKey()}
	 * @return
	 */
	public Iterator<IFlowValidationMessage> getValidationMessages();
	
}
