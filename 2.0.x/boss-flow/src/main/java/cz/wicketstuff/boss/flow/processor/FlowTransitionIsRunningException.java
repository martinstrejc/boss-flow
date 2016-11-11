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
package cz.wicketstuff.boss.flow.processor;

import cz.wicketstuff.boss.flow.FlowException;

/**
 * Exception is thrown when a flow transition had been invoked,
 * that transition had been running till now and another transition
 * was also invoked. It prevents flow to come to an inconsistent state.  
 * 
 * @author Martin Strejc
 *
 */
public class FlowTransitionIsRunningException extends FlowException {

	private static final long serialVersionUID = 1L;

	public FlowTransitionIsRunningException() {
	}

	public FlowTransitionIsRunningException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FlowTransitionIsRunningException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowTransitionIsRunningException(String message) {
		super(message);
	}

	public FlowTransitionIsRunningException(Throwable cause) {
		super(cause);
	}

}
