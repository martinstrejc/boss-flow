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
package cz.wicketstuff.boss.flow.model;

/**
 * The basic interface of virtual states.
 * A virtual state is a state that the flow cannot
 * stand by, but the flow just go through.
 * Example: if condition.
 * 
 * Compare to {@link IFlowRealState}
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowVirtualState extends IFlowState {

}
