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
package com.etnetera.boss.flow.model.basic;

import java.io.Serializable;

import com.etnetera.boss.flow.model.IFlowTransition;
import com.etnetera.boss.flow.model.ISwitchCase;

public class SwitchCase implements ISwitchCase, Serializable {
	
	private static final long serialVersionUID = 1L;

	private String caseName;
	private IFlowTransition transition;
	
	public SwitchCase() {
	}
	
	public SwitchCase(String caseName, IFlowTransition transition) {
		this.caseName = caseName;
		this.transition = transition;
	}

	@Override
	public String getCaseName() {
		return caseName;
	}

	@Override
	public IFlowTransition getTransition() {
		return transition;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public void setTransition(IFlowTransition transition) {
		this.transition = transition;
	}

	@Override
	protected void finalize() throws Throwable {
		transition = null;
		super.finalize();
	}
	
}
