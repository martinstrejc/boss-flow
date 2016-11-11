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
 * Validation results inteface.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowValidation {

	/**
	 * Return true if state has been validated.
	 * 
	 * @return true stae has been validated
	 */
	public boolean isValidated();
		
	/**
	 * Return true if state is valid and it can be shifted by the next transition.
	 * 
	 * @return true if stat is valid
	 */
	public boolean isValid();
	
	/**
	 * Return all validation results from the previous validation.
	 * 
	 * @return validation results
	 */
	public Iterator<IFlowValidationResult> getValidationResults();
	
}