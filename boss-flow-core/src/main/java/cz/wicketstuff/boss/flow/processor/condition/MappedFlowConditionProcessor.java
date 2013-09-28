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
package cz.wicketstuff.boss.flow.processor.condition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowCarter;

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
