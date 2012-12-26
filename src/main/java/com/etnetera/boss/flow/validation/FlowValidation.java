/*
 * Et netera, http://boss.etnetera.cz - Copyright (C) 2012 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 */
package com.etnetera.boss.flow.validation;

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
