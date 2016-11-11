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
package cz.wicketstuff.boss.flow.builder.xml;

import cz.wicketstuff.boss.flow.FlowException;

/**
 * Exception is thrown when a transition has been found more than once
 * in the configuration XML of the flow.
 * 
 * This exception means that the name or the id of a transition is duplicated.
 * 
 * @author Martin Strejc
 *
 */
public class DuplicatedTransitionException extends FlowException {

	private static final long serialVersionUID = 1L;

	public DuplicatedTransitionException() {
	}

	public DuplicatedTransitionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicatedTransitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedTransitionException(String message) {
		super(message);
	}

	public DuplicatedTransitionException(Throwable cause) {
		super(cause);
	}

}
