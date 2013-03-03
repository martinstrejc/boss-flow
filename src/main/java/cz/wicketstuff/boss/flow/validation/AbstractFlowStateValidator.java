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
