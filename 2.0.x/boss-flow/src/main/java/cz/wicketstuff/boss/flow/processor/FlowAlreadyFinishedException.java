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
 * Exception is thrown when somebody tries to invoke a transition on a finished flow.
 * It means a flow that is in a final state.
 *  
 * @author Martin Strejc
 *
 */
public class FlowAlreadyFinishedException extends FlowException {

	private static final long serialVersionUID = 1L;

	public FlowAlreadyFinishedException() {
	}

	public FlowAlreadyFinishedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FlowAlreadyFinishedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowAlreadyFinishedException(String message) {
		super(message);
	}

	public FlowAlreadyFinishedException(Throwable cause) {
		super(cause);
	}

}