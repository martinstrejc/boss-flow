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
