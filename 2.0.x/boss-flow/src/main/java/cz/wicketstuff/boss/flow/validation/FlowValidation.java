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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlowValidation implements IFlowValidation, Serializable {

	private static final long serialVersionUID = 1L;

	private boolean validated;
	private boolean valid;
	private List<IFlowValidationResult> validations;
	
	public FlowValidation() {
		this(false, false);
	}

	public FlowValidation(boolean validated, boolean valid) {
		this(validated, valid, null);
	}

	public FlowValidation(boolean validated, boolean valid,
			List<IFlowValidationResult> validations) {
		this.validated = validated;
		this.valid = valid;
		this.validations = serialVersionOfList(validations);
	}

	@Override
	public boolean isValidated() {
		return validated;
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	@Override
	public Iterator<IFlowValidationResult> getValidationResults() {
		return getValidations().iterator();
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<IFlowValidationResult> getValidations() {
		if(validations == null) {
			validations = new ArrayList<IFlowValidationResult>();
		}
		return validations;
	}

	public void setValidations(List<IFlowValidationResult> validations) {
		this.validations = serialVersionOfList(validations);
	}
	
	public List<IFlowValidationResult> serialVersionOfList(List<IFlowValidationResult> list) {
		if(list == null || list instanceof Serializable) return list;
		return new ArrayList<IFlowValidationResult>(list);
	}

	@Override
	protected void finalize() throws Throwable {
		if(validations != null) {
			validations.clear();
			validations = null;
		}
		super.finalize();
	}

}
