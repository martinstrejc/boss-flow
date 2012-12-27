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

/**
 * @author Martin Strejc
 *
 */
public class FlowMaxHitsReachedException extends FlowException {

	private static final long serialVersionUID = 1L;
	
	private int maxFlowHits;
	private int realFlowHits;

	public FlowMaxHitsReachedException() {
		super();
	}

	public FlowMaxHitsReachedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public FlowMaxHitsReachedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowMaxHitsReachedException(String message) {
		super(message);
	}

	public FlowMaxHitsReachedException(Throwable cause) {
		super(cause);
	}

	public FlowMaxHitsReachedException(int maxFlowHits, int realFlowHits) {
		super();
		this.maxFlowHits = maxFlowHits;
		this.realFlowHits = realFlowHits;
	}

	public int getMaxFlowHits() {
		return maxFlowHits;
	}

	public void setMaxFlowHits(int maxFlowHits) {
		this.maxFlowHits = maxFlowHits;
	}

	public int getRealFlowHits() {
		return realFlowHits;
	}

	public void setRealFlowHits(int realFlowHits) {
		this.realFlowHits = realFlowHits;
	}
	
}
