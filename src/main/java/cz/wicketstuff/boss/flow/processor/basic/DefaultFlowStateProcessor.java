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
package cz.wicketstuff.boss.flow.processor.basic;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.condition.MappedFlowConditionProcessor;
import cz.wicketstuff.boss.flow.processor.condition.NamedFlowSwitchProcessor;

public class DefaultFlowStateProcessor<T extends Serializable> extends SimpleFlowStateProcessor<T> {

	private static final long serialVersionUID = 1L;
	
	private MappedFlowConditionProcessor<T> mappedFlowConditionProcessor = new MappedFlowConditionProcessor<T>();
	private NamedFlowSwitchProcessor<T> namedFlowSwitchProcessor = new NamedFlowSwitchProcessor<T>();
	
	public DefaultFlowStateProcessor() {
		this(null);
	}

	public DefaultFlowStateProcessor(IFlowProcessor<T> flowProcessor) {
		super(flowProcessor);
		setConditionProcessor(mappedFlowConditionProcessor);
		setSwitchProcessor(namedFlowSwitchProcessor);
	}

	public MappedFlowConditionProcessor<T> getMappedFlowConditionProcessor() {
		return mappedFlowConditionProcessor;
	}

	public NamedFlowSwitchProcessor<T> getNamedFlowSwitchProcessor() {
		return namedFlowSwitchProcessor;
	}
	
}
