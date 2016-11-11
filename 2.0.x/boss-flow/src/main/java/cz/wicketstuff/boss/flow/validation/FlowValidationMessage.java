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
