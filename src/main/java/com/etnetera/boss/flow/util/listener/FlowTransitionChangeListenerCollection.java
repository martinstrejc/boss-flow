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
import com.etnetera.boss.flow.processor.IFlowTransitionChangeListener;
import com.etnetera.boss.flow.util.Comparators;
import com.etnetera.boss.flow.util.FlowListenerCollection;

public class FlowTransitionChangeListenerCollection<T extends Serializable> extends
		FlowListenerCollection<IFlowTransitionChangeListener<T>> implements
		IFlowTransitionChangeListener<T> {

	private static final long serialVersionUID = 1L;

	private int priority = 0;
	
	public FlowTransitionChangeListenerCollection() {
		super();
	}

	public FlowTransitionChangeListenerCollection(int priority) {
		super();
		this.priority = priority;
	}

	@Override
	public void onTransitionStart(final IFlowCarter<T> flow) {
		notify(new INotifier<IFlowTransitionChangeListener<T>>() {
			@Override
			public void notify(IFlowTransitionChangeListener<T> listener) {
				listener.onTransitionStart(flow);
			}
		});
	}

	@Override
	public void onTransitionFinished(final IFlowCarter<T> flow) {
		notify(new INotifier<IFlowTransitionChangeListener<T>>() {
			@Override
			public void notify(IFlowTransitionChangeListener<T> listener) {
				listener.onTransitionFinished(flow);
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
