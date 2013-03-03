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
 * Annotation to mark a method to be a listener of a validation event change.
 * When the the criteria held by this annotation matches
 * method is invoked when state is changed.
 * 
 * See {@link IFlowStateChangeListener} to understand state listeners.
 * 
 * Example:
 * 
 * In the following example the listener method is called to notify invalid state.
 * 
 *	&#64;FlowStateValidation(stateNameRegex="entryPersonalData.*", event=ValidationEvent.invalid priority=2)
 *	public void onInvalidPersonalData(IFlowCarter&lt;?&gt;> flowCarter) {
 *		... do something ...
 *	}
 *
 * @author Martin Strejc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlowStateValidation {

	/**
	 * Enumeration of validation events to notify the listener method.
	 * 
	 * @author Martin Strejc
	 *
	 */
	public static enum ValidationEvent {
		/**
		 * notify just valid states
		 */
		valid, 
		
		/**
		 * notify just invalid states
		 */
		invalid, 
		
		/**
		 * notify both of valid or invalid state
		 */
		all;
	}
	
	/**
	 * The state validation status to notify the listener method.
	 * 
	 * @return state validation status to notify the listener
	 */
	ValidationEvent event() default ValidationEvent.all;
	
	/**
	 * The regular expression that has validated state must match
	 * to notify. Value "" means this filter is not applied.
	 * 
	 * @return regular expression state name filter
	 */
	String stateNameRegex() default "";
	
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
