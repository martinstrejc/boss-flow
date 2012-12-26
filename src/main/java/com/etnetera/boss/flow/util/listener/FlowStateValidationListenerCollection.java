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
package com.etnetera.boss.flow.util.listener;

import java.io.Serializable;

import com.etnetera.boss.flow.model.IFlowCarter;
import com.etnetera.boss.flow.processor.IFlowStateValidationListener;
import com.etnetera.boss.flow.util.Comparators;
import com.etnetera.boss.flow.util.FlowListenerCollection;

public class FlowStateValidationListenerCollection<T extends Serializable> extends
		FlowListenerCollection<IFlowStateValidationListener<T>> implements
		IFlowStateValidationListener<T> {

	private static final long serialVersionUID = 1L;
	
	private int priority = 0;
	
	public FlowStateValidationListenerCollection() {
		super();
	}
	
	public FlowStateValidationListenerCollection(int priority) {
		super();
		this.priority = priority;
	}

	@Override
	public void onStateValid(final IFlowCarter<T> flow) {
		notify(new INotifier<IFlowStateValidationListener<T>>() {
			@Override
			public void notify(IFlowStateValidationListener<T> listener) {
				listener.onStateValid(flow);
			}
		});
	}

	@Override
	public void onStateInvalid(final IFlowCarter<T> flow) {
		notify(new INotifier<IFlowStateValidationListener<T>>() {
			@Override
			public void notify(IFlowStateValidationListener<T> listener) {
				listener.onStateInvalid(flow);
			}
		});
	}

	@Override
	public int compareTo(IPriority o) {
		return Comparators.compareInts(priority, o.getPriority());
	}

	@Override
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
