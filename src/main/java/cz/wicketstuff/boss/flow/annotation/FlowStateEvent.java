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
package cz.wicketstuff.boss.flow.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.IFlowStateChangeListener;

/**
 * Annotation to mark a method to be a listener of a state change.
 * When the the criteria held by this annotation matches
 * method is invoked when state is changed.
 * 
 * See {@link IFlowStateChangeListener} to understand state listeners.
 * 
 * Example:
 * 
 * In the following example the listener method is called to notify state change.
 * 
 *	&#64;FlowStateEvent(event=StateEvent.onStateEntry, priority=2)
 *	public void onStepEntry(IFlowCarter&lt;?&gt;> flowCarter) {
 *		... do something ...
 *	}
 *
 * @author Martin Strejc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlowStateEvent {

	/**
	 * Enumeration of events to notify the listener method.
	 * 
	 * @author Martin Strejc
	 *
	 */
	public static enum StateEvent {
		
		/**
		 * entry the state
		 */
		onStateEntry, 
		
		/**
		 * leave the state 
		 */
		onStateLeaving, 
		
		/**
		 * both of entry and leave
		 */
		all;
	}
	
	/**
	 * The state event to notify the listener method.
	 * 
	 * @return state event to notify the listener
	 */
	StateEvent event() default StateEvent.all;
	
	/**
	 * The regular expression that has the entered or left state 
	 * to notify. Value "" means this filter is not applied.
	 * 
	 * @return regular expression state name filter
	 */
	String stateNameRegex() default "";

	/**
	 * The java regular expression the state category name must match.
	 * The "" value means any category name
	 * 
	 * @return regular expression to match condition name
	 */
	String categoryNameRegex() default "";
	
	/**
	 * The class that a state has to implement
	 * to annotated method can be used as a listener.
	 * 
	 * @return flow state implementing interface filter
	 */
	Class<? extends IFlowState> type() default IFlowState.class;

	/**
	 * The priority that this listener has.
	 * If there are more than one listener attached to the same state
	 * or state action. The priority is important to decide who has 
	 * to be notified first.
	 * 
	 * The smallest value mean to be notified the first. Default value is 0.
	 * 
	 * All listener's methods have to be sorted by priority 
	 * and than they are notified just if the filter matches its criteria.
	 * 
	 * @return priority of this listener
	 */
	int priority() default 0;
	
}
