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

/**
 * Annotation to mark a method to be a listener of a transition events.
 * When the the criteria held by this annotation matches
 * method is invoked when transition is started or finished.
 * 
 * 
 * Example:
 * 
 * In the following example the listener method is called to notify state change.
 * 
 *	&#64;FlowTransitionEvent(transitionNameRegex="nextStep.*", event=TransitionEvent.onTransitionStart)
 *	public void onStepEntry(IFlowCarter&lt;?&gt;> flowCarter) {
 *		... do something ...
 *	}
 *
 * @author Martin Strejc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlowTransitionEvent {

	/**
	 * Enumeration of transition events to notify the listener method.
	 * 
	 * @author Martin Strejc
	 *
	 */
	public static enum TransitionEvent {
		/**
		 * transition is going to start
		 */
		onTransitionStart, 
		
		/**
		 * transition finished
		 */
		onTransitionFinished, 
		
		/**
		 * both of start and finish transition
		 */
		all;
	}
	
	/**
	 * The transition event to notify the listener method.
	 * 
	 * @return transition event to notify the listener
	 */
	TransitionEvent event() default TransitionEvent.all;
	
	/**
	 * The regular expression that the transition must mutch
	 * to notify. Value "" means this filter is not applied.
	 * 
	 * @return regular expression transition name filter
	 */
	String transitionNameRegex() default "";
	
	/**
	 * The priority that this listener has.
	 * If there are more than one listener attached to the same transition
	 * or transition event. The priority is important to decide who has 
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
