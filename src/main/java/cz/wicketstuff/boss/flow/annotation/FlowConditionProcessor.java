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

import cz.wicketstuff.boss.flow.model.IFlowConditionState;
import cz.wicketstuff.boss.flow.model.IFlowState;

/**
 * Annotation to mark a method to be a condition expression.
 * When the expression matches this criteria held by this annotation,
 * the result of the method is used as the result of the expression.
 * 
 * See {@link IFlowConditionState} to understand condition states.
 * 
 * Example:
 * 
 * In the following example the condition method is called to get the result
 * of any condition starting with 'condition' prefix.
 * 
 *	 &#64;FlowConditionProcessor(conditionExpressionRegex = "condition.*")
 *   public boolean isConditionTrue(String condition, IFlowCarter&lt;?&gt;> flowCarter) {
 * 	   return true;
 *   }
 * 
 * @author Martin Strejc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlowConditionProcessor {

	/**
	 * The java regular expression this condition must match.
	 * The "" value means any expressions
	 * 
	 * @return regular expression to match condition name
	 */
	String conditionExpressionRegex() default "";
	
	/**
	 * The java regular expression the state name must match.
	 * The annotated method can be associated just with a set
	 * of condition states.
	 * The "" value means any state names
	 * 
	 * @return regular expression to match state name
	 */
	String stateNameRegex() default "";
	
	/**
	 * The class that a state has to implement
	 * to annotated method can be used as the condition expression.
	 * The annotated method can be associated just with a set
	 * of states filtered by implementing interface.
	 * 
	 * @return flow state implementing interface filter
	 */
	Class<? extends IFlowState> type() default IFlowState.class;
	
}
