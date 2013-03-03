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

public class FlowValidationResult implements IFlowValidationResult, Serializable {

	private static final long serialVersionUID = 1L;

	private String itemKey;
	private boolean valid;

	private List<IFlowValidationMessage> messages;
	
	public FlowValidationResult() {
		this(null, false);
	}

	public FlowValidationResult(String itemKey, boolean valid) {
		this(itemKey, valid, null);
	}

	public FlowValidationResult(String itemKey, boolean valid,
			List<IFlowValidationMessage> messages) {
		this.itemKey = itemKey;
		this.valid = valid;
		this.messages = serialVersionOfList(messages);
	}

	@Override
	public String getItemKey() {
		return itemKey;
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	@Override
	public Iterator<IFlowValidationMessage> getValidationMessages() {
		return getMessages().iterator();
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
		
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public List<IFlowValidationMessage> getMessages() {
		if(messages == null) {
			messages = new ArrayList<IFlowValidationMessage>();
		}
		return messages;
	}

	public void setMessages(List<IFlowValidationMessage> messages) {
		this.messages = serialVersionOfList(messages);
	}
	
	public List<IFlowValidationMessage> serialVersionOfList(List<IFlowValidationMessage> list) {
		if(list == null || list instanceof Serializable) return list;
		return new ArrayList<IFlowValidationMessage>(list);
	}
	
	@Override
	protected void finalize() throws Throwable {
		if(messages != null) {
			messages.clear();
			messages = null;
		}
		super.finalize();
	}

}
