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
package cz.wicketstuff.boss.flow.model.basic;

import java.util.Map;

import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.IFlowVirtualState;

public class FlowVirtualState extends FlowState implements IFlowVirtualState {

	private static final long serialVersionUID = 1L;

	public FlowVirtualState() {
		super();
	}

	public FlowVirtualState(String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(null, stateName, transitionsMap);
	}

	public FlowVirtualState(String stateName) {
		super(stateName);
	}

	public FlowVirtualState(Integer stateId, String stateName,
			Map<String, IFlowTransition> transitionsMap) {
		super(stateId, stateName, transitionsMap);
	}

	public FlowVirtualState(Integer stateId, String stateName) {
		super(stateId, stateName);
	}
	
	
	
}
