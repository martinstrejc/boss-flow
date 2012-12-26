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
package com.etnetera.boss.flow.processor.basic;

import java.io.Serializable;

import com.etnetera.boss.flow.FlowException;
import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.model.basic.FlowCarter;
import com.etnetera.boss.flow.processor.IFlowCarterFactory;

public class DefaultFlowCarterFactory<T extends Serializable> implements IFlowCarterFactory<T> {

	public IFlowCarter<T> createFlowCarter(Long flowProcessId, IFlowState startState) throws FlowException {
		return new FlowCarter<T>(flowProcessId, startState);
	}

}
