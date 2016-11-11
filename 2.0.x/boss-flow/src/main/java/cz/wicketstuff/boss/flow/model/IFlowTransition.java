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
package cz.wicketstuff.boss.flow.model;

import java.io.Serializable;

/**
 * Interface of transition defines just its abstract methods.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowTransition extends IFlowCategorized, Serializable {

	/**
	 * Return the unique numeric identifier of this transition.
	 * 
	 * @return unique numeric identifier 
	 */
	public Integer getTransitionId();

	/**
	 * Return the unique string identifier of this transition.
	 * 
	 * @return unique string identifier
	 */
	public String getTransitionName();
	
	/**
	 * Return the state that this transition shit flow to.
	 * 
	 * @return target state to shift flow
	 */
	public IFlowState getTargetState();
	
	/**
	 * Return {@code true} if state hit of flow can be counted
	 * when this transition is finished.
	 * 
	 * @return {@code true} if state hit of flow can be counted
	 */
	public boolean isHitCountable();
	
	/**
	 * Java hashCode method derivated from {@link Object}
	 * 
	 * @return hash code
	 */
	public int hashCode();
	
	/**
	 * Java equals method derivated from {@link Object}
	 * 
	 * @return hash code
	 */
	public boolean equals(Object obj);


}