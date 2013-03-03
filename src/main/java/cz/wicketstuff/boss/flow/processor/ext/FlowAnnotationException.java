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
package cz.wicketstuff.boss.flow.processor.ext;

import cz.wicketstuff.boss.flow.FlowException;

public class FlowAnnotationException extends FlowException {

	private static final long serialVersionUID = 1L;

	public FlowAnnotationException() {
		super();
	}

	public FlowAnnotationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FlowAnnotationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowAnnotationException(String message) {
		super(message);
	}

	public FlowAnnotationException(Throwable cause) {
		super(cause);
	}


}
