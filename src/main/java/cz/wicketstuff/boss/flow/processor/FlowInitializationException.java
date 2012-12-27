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
package cz.wicketstuff.boss.flow.processor;

import cz.wicketstuff.boss.flow.FlowException;

public class FlowInitializationException extends FlowException {

	private static final long serialVersionUID = 1L;

	public FlowInitializationException() {
	}

	public FlowInitializationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FlowInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowInitializationException(String message) {
		super(message);
	}

	public FlowInitializationException(Throwable cause) {
		super(cause);
	}

}
