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
package cz.wicketstuff.boss.flow.validation;

import java.io.Serializable;
import java.util.Iterator;

import cz.wicketstuff.boss.flow.model.IFlowCarter;

public abstract class AbstractFlowStateValidator<T extends Serializable> implements IFlowStateValidator<T> {

	public AbstractFlowStateValidator() {
	}

	@Override
	public IFlowValidation validateState(IFlowCarter<T> flow) {
		FlowValidation validation = new FlowValidation();
		if(flow.getCurrentState().isStateValidatable()) {
			validate(flow, validation);
			validation.setValid(true);
			for(Iterator<IFlowValidationResult> ri = validation.getValidationResults(); ri.hasNext();) {
				if(!ri.next().isValid()) {
					validation.setValid(false);
					break;
				}
			}
			validation.setValidated(true);
		}
		return validation;
	}
	
	protected abstract void validate(IFlowCarter<T> flow, IFlowValidation validation);

}
