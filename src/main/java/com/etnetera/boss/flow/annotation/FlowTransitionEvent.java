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
package com.etnetera.boss.flow.annotation;

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
 *	@FlowTransitionEvent(transitionNameRegex="nextStep.*", event=TransitionEvent.onTransitionStart)
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
