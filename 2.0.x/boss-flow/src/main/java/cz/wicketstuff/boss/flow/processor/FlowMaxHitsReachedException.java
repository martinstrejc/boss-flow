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
package cz.wicketstuff.boss.flow.processor;

import cz.wicketstuff.boss.flow.FlowException;

/**
 * Exception is thrown when maxHits property has been sets and flow
 * has been transited more time that maxHits allows. It prevents flow
 * to be cycled by an unfinite loop.
 * 
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
