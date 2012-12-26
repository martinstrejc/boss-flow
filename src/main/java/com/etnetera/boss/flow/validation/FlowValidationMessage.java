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

public class FlowValidationMessage implements IFlowValidationMessage, Serializable {

	private static final long serialVersionUID = 1L;

	private String messageKey;
	private String message;
	private Serializable data;

	public FlowValidationMessage() {
		this(null, null);
	}

	public FlowValidationMessage(String messageKey, String message) {
		this(messageKey, message, null);
	}

	public FlowValidationMessage(String messageKey, String message,
			Serializable data) {
		this.messageKey = messageKey;
		this.message = message;
		this.data = data;
	}

	@Override
	public String getMessageKey() {
		return messageKey;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Serializable getData() {
		return data;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Serializable data) {
		this.data = data;
	}
	
	@Override
	protected void finalize() throws Throwable {
		data = null;
		super.finalize();
	}


}
