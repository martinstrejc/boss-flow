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
package cz.wicketstuff.boss.flow.processor.ext;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.annotation.FlowConditionProcessor;
import cz.wicketstuff.boss.flow.annotation.FlowEvents;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateValidation;
import cz.wicketstuff.boss.flow.annotation.FlowSwitchProcessorExpression;
import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.processor.condition.CannotProcessConditionException;
import cz.wicketstuff.boss.flow.util.listener.FlowListenersCollection;
import cz.wicketstuff.boss.flow.util.listener.FlowStateChangeListenerCollection;
import cz.wicketstuff.boss.flow.util.listener.FlowStateValidationListenerCollection;
import cz.wicketstuff.boss.flow.util.listener.FlowTransitionChangeListenerCollection;
import cz.wicketstuff.boss.flow.util.processor.FlowConditionStateProcessorCollection;
import cz.wicketstuff.boss.flow.util.processor.FlowSwitchStateProcessorCollection;

public class AnnotationFlowFactory<T extends Serializable> {

	protected static final Logger logger = LoggerFactory.getLogger(AnnotationFlowFactory.class);
	
	public AnnotationFlowFactory() {
	}
	
	public FlowListenersCollection<T> newFlowListenersCollection() {
		return new FlowListenersCollection<T>();
	}  
	
	public FlowListenersCollection<T> getFlowListeners(Object bean) throws FlowAnnotationException {
		return getFlowListeners(bean, newFlowListenersCollection());
	}

	public FlowListenersCollection<T> getFlowListeners(final Object bean, FlowListenersCollection<T> listeners) throws FlowAnnotationException {
		for(final Method method : findMethodCandidates(bean, FlowEvents.class)) {
			FlowEvents eventAnnotation = method.getAnnotation(FlowEvents.class);
			if(logger.isDebugEnabled()) {
				logger.debug("Adding annoted FlowEvents method '" + method.getName() + "' of bean '" + bean + "'");
			}
			listeners.add(new FilteredFlowListener<T>(eventAnnotation.event(), eventAnnotation.priority()) {

				
				
				private static final long serialVersionUID = 1L;

				@Override
				protected void onFlowInitializedFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "' of bean '" + bean + "' because: " + e.getMessage(), e);
					}
					
				}

				@Override
				protected void onFlowFinishedFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method of bean '" + bean + "' because: " + e.getMessage(), e);
					}
				}
				
			});
		}
		listeners.sort();
		return listeners;
	}
	
	public FlowStateChangeListenerCollection<T> newFlowStateChangeListenerCollection() {
		return new FlowStateChangeListenerCollection<T>();
	}  
	
	public FlowStateChangeListenerCollection<T> getStateChangeListeners(Object bean) throws FlowAnnotationException {
		return getStateChangeListeners(bean, newFlowStateChangeListenerCollection());
	}

	public FlowStateChangeListenerCollection<T> getStateChangeListeners(final Object bean, FlowStateChangeListenerCollection<T> listeners) throws FlowAnnotationException {
		for(final Method method : findMethodCandidates(bean, FlowStateEvent.class)) {
			FlowStateEvent eventAnnotation = method.getAnnotation(FlowStateEvent.class);
			if(logger.isDebugEnabled()) {
				logger.debug("Adding annoted FlowStateEvent method '" + method.getName() + "' of bean '" + bean + "'");
			}
			listeners.add(new FilteredStateChangeListener<T>(eventAnnotation.event(), "".equals(eventAnnotation.stateNameRegex()) ? null : eventAnnotation.stateNameRegex(), eventAnnotation.type(), eventAnnotation.priority()) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onStateEntryFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "' of bean '" + bean + "' because: " + e.getMessage(), e);
					}
					
				}

				@Override
				protected void onStateLeavingFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method of bean '" + bean + "' because: " + e.getMessage(), e);
					}
				}
				
			});
		}
		listeners.sort();
		return listeners;
	}

	public FlowStateValidationListenerCollection<T> newFlowStateValidationListenerCollection() {
		return new FlowStateValidationListenerCollection<T>();
	}
	
	public FlowStateValidationListenerCollection<T> getStateValidationListeners(Object bean) throws FlowAnnotationException {
		return getStateValidationListeners(bean, newFlowStateValidationListenerCollection());
	}

	public FlowStateValidationListenerCollection<T> getStateValidationListeners(final Object bean, FlowStateValidationListenerCollection<T> listeners) throws FlowAnnotationException {
		for(final Method method : findMethodCandidates(bean, FlowStateValidation.class)) {
			FlowStateValidation eventAnnotation = method.getAnnotation(FlowStateValidation.class);
			if(logger.isDebugEnabled()) {
				logger.debug("Adding annoted FlowStateValidation method '" + method.getName() + "' of bean '" + bean + "'");
			}
			listeners.add(new FilteredStateValidationListener<T>(eventAnnotation.event(), "".equals(eventAnnotation.stateNameRegex()) ? null : eventAnnotation.stateNameRegex(), eventAnnotation.type(), eventAnnotation.priority()) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onStateValidFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "' of bean '" + bean + "' because: " + e.getMessage(), e);
					}					
				}

				@Override
				protected void onStateInvalidFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "' of bean '" + bean + "' because: " + e.getMessage(), e);
					}
				}
				
			});
		}
		listeners.sort();
		return listeners;
	}

	public FlowTransitionChangeListenerCollection<T> newFlowTransitionChangeListenerCollection() {
		return new FlowTransitionChangeListenerCollection<T>();
	}
	
	public FlowTransitionChangeListenerCollection<T> getTransitionChangeListeners(Object bean) throws FlowAnnotationException {
		return getTransitionChangeListeners(bean, newFlowTransitionChangeListenerCollection());
	}

	public FlowTransitionChangeListenerCollection<T> getTransitionChangeListeners(final Object bean, FlowTransitionChangeListenerCollection<T> listeners) throws FlowAnnotationException {
		for(final Method method : findMethodCandidates(bean, FlowTransitionEvent.class)) {
			FlowTransitionEvent eventAnnotation = method.getAnnotation(FlowTransitionEvent.class);
			if(logger.isDebugEnabled()) {
				logger.debug("Adding annoted FlowTransitionEvent change listener method '" + method.getName() + "' of bean '" + bean + "'");
			}
			listeners.add(new FilteredTransitionChangeListener<T>(eventAnnotation.event(), "".equals(eventAnnotation.transitionNameRegex()) ? null : eventAnnotation.transitionNameRegex(), eventAnnotation.priority()) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onTransitionStartFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "' of bean '" + bean + "' because: " + e.getMessage(), e);
					}
				}

				@Override
				protected void onTransitionFinishedFiltered(IFlowCarter<T> flow) {
					try {
						method.invoke(bean, flow);
					} catch ( IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "' of bean '" + bean + "' because: " + e.getMessage(), e);
					}
				}
				
			});
			
		}
		listeners.sort();
		return listeners;
	}

	public FlowConditionStateProcessorCollection<T> newFlowConditionStateCollection() {
		return new FlowConditionStateProcessorCollection<T>();
	}
	
	public FlowConditionStateProcessorCollection<T> getFlowConditionProcessors(Object bean) throws FlowAnnotationException {
		return getFlowConditionProcessors(bean, newFlowConditionStateCollection());
	}
	
	public FlowConditionStateProcessorCollection<T> getFlowConditionProcessors(final Object bean, FlowConditionStateProcessorCollection<T> processorCollection) throws FlowAnnotationException {
		for(final Method method : findMethodConditionCandidates(bean, FlowConditionProcessor.class)) {
			FlowConditionProcessor conditionAnnotation = method.getAnnotation(FlowConditionProcessor.class);
			if(logger.isDebugEnabled()) {
				logger.debug("Adding annoted FlowConditionProcessor method '" + method.getName() + "' of bean '" + bean + "'");
			}
			processorCollection.add(
					new FilteredStateConditionProcessor<T>(
							"".equals(conditionAnnotation.conditionExpressionRegex()) ? null : conditionAnnotation.conditionExpressionRegex(),
							"".equals(conditionAnnotation.stateNameRegex()) ? null : conditionAnnotation.stateNameRegex(), 
							conditionAnnotation.type()) {

						private static final long serialVersionUID = 1L;

						@Override
						public boolean ifCondition(String conditionExpression,
								IFlowCarter<T> flow)
								throws CannotProcessConditionException {
							try {
								return (boolean) method.invoke(bean, conditionExpression, flow);
							} catch ( IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
								throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "', condition '" + conditionExpression + ", of bean " + bean + "' because: " + e.getMessage(), e);
							}
						}
			});
		}
		return processorCollection;
	}

	public FlowSwitchStateProcessorCollection<T> newFlowSwitchStateProcessorCollection() {
		return new FlowSwitchStateProcessorCollection<T>();
	}
	
	public FlowSwitchStateProcessorCollection<T> getFlowSwitchProcessors(Object bean) throws FlowAnnotationException {
		return getFlowSwitchProcessors(bean, newFlowSwitchStateProcessorCollection());
	}
	
	public FlowSwitchStateProcessorCollection<T> getFlowSwitchProcessors(final Object bean, FlowSwitchStateProcessorCollection<T> processorCollection) throws FlowAnnotationException {
		for(final Method method : findMethodSwitchCandidates(bean, FlowSwitchProcessorExpression.class)) {
			FlowSwitchProcessorExpression conditionAnnotation = method.getAnnotation(FlowSwitchProcessorExpression.class);
			if(logger.isDebugEnabled()) {
				logger.debug("Adding annoted FlowSwitchProcessorExpression method '" + method.getName() + "' of bean '" + bean + "'");
			}
			processorCollection.add(
					new FilteredStateSwitchProcessor<T>(
							"".equals(conditionAnnotation.switchExpressionRegex()) ? null : conditionAnnotation.switchExpressionRegex(),
							"".equals(conditionAnnotation.stateNameRegex()) ? null : conditionAnnotation.stateNameRegex(), 
							conditionAnnotation.type()) {

						private static final long serialVersionUID = 1L;

						@Override
						public String resolveSwitchExpression(
								IFlowCarter<T> flow, String switchExpression) {
							try {
								return (String) method.invoke(bean, switchExpression, flow);
							} catch ( IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
								throw new IllegalStateException("Cannot invoke annoted method '" + method.getName() + "', condition '" + switchExpression + ", of bean '" + bean + "' because: " + e.getMessage(), e);
							}
						}

			});
		}
		return processorCollection;
	}
	
	public List<Method> findMethodCandidates(Object bean, Class<? extends Annotation> annotation) throws FlowAnnotationException {
		List<Method> list = new ArrayList<Method>();
		for(final Method method : bean.getClass().getMethods()) {
			if(method.isAnnotationPresent(annotation)) {
				checkFlowMethod(method, bean);
				list.add(method);
			}
		}
		return list;
	}

	public List<Method> findMethodConditionCandidates(Object bean, Class<? extends Annotation> annotation) throws FlowAnnotationException {
		List<Method> list = new ArrayList<Method>();
		for(final Method method : bean.getClass().getMethods()) {
			if(method.isAnnotationPresent(annotation)) {
				checkConditionMethod(method, bean);
				list.add(method);
			}
		}
		return list;
	}

	public List<Method> findMethodSwitchCandidates(Object bean, Class<? extends Annotation> annotation) throws FlowAnnotationException {
		List<Method> list = new ArrayList<Method>();
		for(final Method method : bean.getClass().getMethods()) {
			if(method.isAnnotationPresent(annotation)) {
				checkSwitchMethod(method, bean);
				list.add(method);
			}
		}
		return list;
	}
	
	public void checkFlowMethod(Method method, Object bean) throws FlowAnnotationException {
		Class<?>[] parameters = method.getParameterTypes();
		if(parameters.length != 1) {
			throw new FlowAnnotationException("The method '" + method.getName() + "' of bean '" + bean + "' has to have just one parameter, not more, not less!");
		}
		try {
			if(parameters[0].asSubclass(IFlowCarter.class) == null) {
				throw new FlowAnnotationException("The paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of IFlowCarter<T extends Serializable>!");
			}					
		} catch (ClassCastException e) {					
			throw new FlowAnnotationException("The paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of IFlowCarter<T extends Serializable>!", e);
		}
		if(!Modifier.isPublic(method.getModifiers())) {
			throw new FlowAnnotationException("The paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not public!");					
		}
	}

	public void checkConditionMethod(Method method, Object bean) throws FlowAnnotationException {
		Class<?>[] parameters = method.getParameterTypes();
		if(parameters.length != 2) {
			throw new FlowAnnotationException("The method '" + method.getName() + "' of bean '" + bean + "' has to have just one parameter, not more, not less! Correct parameters are String, IFlowCarter<T extends Serializable>");
		}
		try {
			if(method.getReturnType().asSubclass(boolean.class) == null) {
				throw new FlowAnnotationException("The return type of method '" + method.getName() + "' of bean '" + bean + "' is not a type of boolean!");
			}					
		} catch (ClassCastException e) {					
			throw new FlowAnnotationException("The return type method '" + method.getName() + "' of bean '" + bean + "' is not a type of boolean!", e);
		}
		try {
			if(parameters[0].asSubclass(String.class) == null) {
				throw new FlowAnnotationException("The first paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of String!");
			}					
		} catch (ClassCastException e) {					
			throw new FlowAnnotationException("The first paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of String!", e);
		}
		try {
			if(parameters[1].asSubclass(IFlowCarter.class) == null) {
				throw new FlowAnnotationException("The second paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of IFlowCarter<T extends Serializable>!");
			}					
		} catch (ClassCastException e) {					
			throw new FlowAnnotationException("The second paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of IFlowCarter<T extends Serializable>!", e);
		}
		if(!Modifier.isPublic(method.getModifiers())) {
			throw new FlowAnnotationException("The method '" + method.getName() + "' of bean '" + bean + "' is not public!");					
		}
	}


	public void checkSwitchMethod(Method method, Object bean) throws FlowAnnotationException {
		Class<?>[] parameters = method.getParameterTypes();
		if(parameters.length != 2) {
			throw new FlowAnnotationException("The method '" + method.getName() + "' of bean '" + bean + "' has to have just one parameter, not more, not less! Correct parameters are String, IFlowCarter<T extends Serializable>");
		}
		try {
			if(method.getReturnType().asSubclass(String.class) == null) {
				throw new FlowAnnotationException("The return type of method '" + method.getName() + "' of bean '" + bean + "' is not a type of String!");
			}					
		} catch (ClassCastException e) {					
			throw new FlowAnnotationException("The return type method '" + method.getName() + "' of bean '" + bean + "' is not a type of boolean!", e);
		}
		try {
			if(parameters[0].asSubclass(String.class) == null) {
				throw new FlowAnnotationException("The first paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of String!");
			}					
		} catch (ClassCastException e) {					
			throw new FlowAnnotationException("The first paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of String!", e);
		}
		try {
			if(parameters[1].asSubclass(IFlowCarter.class) == null) {
				throw new FlowAnnotationException("The second paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of IFlowCarter<T extends Serializable>!");
			}					
		} catch (ClassCastException e) {					
			throw new FlowAnnotationException("The second paremeter of method '" + method.getName() + "' of bean '" + bean + "' is not a type of IFlowCarter<T extends Serializable>!", e);
		}
		if(!Modifier.isPublic(method.getModifiers())) {
			throw new FlowAnnotationException("The method '" + method.getName() + "' of bean '" + bean + "' is not public!");					
		}
	}


}
