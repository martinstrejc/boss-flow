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
package com.etnetera.boss.flow.builder.xml;

import com.etnetera.boss.flow.FlowException;

/**
 * Exception is thrown when a transition has been found more than once
 * in the configuration XML of the flow.
 * 
 * This exception means that the name or the id of transition is duplicated.
 * 
 * @author Martin Strejc
 *
 */
public class DuplicateTransitionException extends FlowException {

	private static final long serialVersionUID = 1L;

	public DuplicateTransitionException() {
	}

	public DuplicateTransitionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateTransitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateTransitionException(String message) {
		super(message);
	}

	public DuplicateTransitionException(Throwable cause) {
		super(cause);
	}

}
