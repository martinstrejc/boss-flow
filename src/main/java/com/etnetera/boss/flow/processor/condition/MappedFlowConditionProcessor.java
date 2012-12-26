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
package com.etnetera.boss.flow.processor.condition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.etnetera.boss.flow.model.IFlowCarter;

public class MappedFlowConditionProcessor<T extends Serializable> implements IFlowConditionProcessor<T>, Serializable {
	
	private static final long serialVersionUID = 1L;

	private Map<String, Boolean> resultMap;
	private boolean defaultValue;
	
	public MappedFlowConditionProcessor() {
		this(new HashMap<String, Boolean>(), false);
	}
	
	public MappedFlowConditionProcessor(Map<String, Boolean> resultMap,
			boolean defaultValue) {
		this.resultMap = resultMap;
		this.defaultValue = defaultValue;
	}

	public boolean ifCondition(String conditionExpression, IFlowCarter<T> flow) {
		Boolean b = getResultMap().get(conditionExpression);
		if(b == null) {
			return isDefaultValue();
		}
		return b;
	}

	public Map<String, Boolean> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Boolean> resultMap) {
		this.resultMap = resultMap;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	protected void finalize() throws Throwable {
		if(resultMap != null) {
			resultMap.clear();
			resultMap = null;
		}
		super.finalize();
	}

}
