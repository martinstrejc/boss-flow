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

import com.etnetera.boss.flow.model.IFlowState;
import com.etnetera.boss.flow.model.IFlowSwitchState;

/**
 * Annotation to mark a method to be a condition expression.
 * When the expression matches this criteria held by this annotation,
 * the result of the method is used as the result of the expression.
 * 
 * See {@link IFlowSwitchState} to understand switch states.
 * 
 * Example:
 * 
 * In the following example the condition method is called to get the result
 * of any condition starting with 'condition' prefix.
 * 
 *	 &#64;FlowConditionProcessor(switchExpressionRegex = "switch.*")
 *   public boolean swtichCondition(String condition, IFlowCarter&lt;?&gt;> flowCarter) {
 * 	   return true;
 *   }
 * 
 * @author Martin Strejc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlowSwitchProcessorExpression {

	/**
	 * The regular expression that condition name must match
	 * to notify. Value "" means this filter is not applied.
	 * 
	 * @return regular expression state name filter
	 */
	String switchExpressionRegex() default "";
	
	/**
	 * The regular expression that state must match
	 * to notify. Value "" means this filter is not applied.
	 * 
	 * @return regular expression state name filter
	 */
	String stateNameRegex() default "";
	
	/**
	 * The class that a state has to implement
	 * to annotated method can be used as switch expression.
	 * 
	 * @return flow state implementing interface filter
	 */
	Class<? extends IFlowState> type() default IFlowState.class;
	
}
