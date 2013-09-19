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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.wicketstuff.boss.flow.annotation.FlowConditionProcessor;
import cz.wicketstuff.boss.flow.annotation.FlowEvents;
import cz.wicketstuff.boss.flow.annotation.FlowEvents.FlowEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent.StateEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateValidation;
import cz.wicketstuff.boss.flow.annotation.FlowStateValidation.ValidationEvent;
import cz.wicketstuff.boss.flow.annotation.FlowSwitchProcessorExpression;
import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent;
import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent.TransitionEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.basic.FlowCarter;
import cz.wicketstuff.boss.flow.model.basic.FlowState;
import cz.wicketstuff.boss.flow.model.basic.FlowTransition;
import cz.wicketstuff.boss.flow.processor.IFlowListener;
import cz.wicketstuff.boss.flow.processor.IFlowStateChangeListener;
import cz.wicketstuff.boss.flow.processor.IFlowStateValidationListener;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionChangeListener;
import cz.wicketstuff.boss.flow.processor.condition.CannotProcessConditionException;
import cz.wicketstuff.boss.flow.processor.condition.IFlowConditionProcessor;
import cz.wicketstuff.boss.flow.processor.condition.IFlowSwitchProcessor;

/**
 * @author Martin Strejc
 *
 */
public class AnnotationFlowFactoryTest {

	private AnnotationFlowFactory<String> annotationFactory = new AnnotationFlowFactory<>();
	
	private boolean notify1;
	private boolean notify2;
	

	@Before
	public void setUp() throws Exception {
		clearResults();
	}
	
	public void clearResults() {
		notify1 = false;
		notify1 = false;
		notify1 = false;		
	}
	
	private IFlowCarter<String> createFlowCarter() {
		FlowCarter<String> carter = new FlowCarter<String>(1L, new FlowState("testState"));
		carter.setPreviousTransition(new FlowTransition(1, "previousTransition"));
		carter.setNextTransition(new FlowTransition(2, "nextTransition"));
		return carter;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetFlowListenersObjectExcp1() throws FlowAnnotationException {
		annotationFactory.getFlowListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowEvents
			public void notifier1() {
				notify1 = true;
			}
			
		});		
	}		

	@Test(expected=FlowAnnotationException.class)
	public void testGetFlowListenersObjectExcp2() throws FlowAnnotationException {
		annotationFactory.getFlowListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowEvents
			public void notifier1(String payload) {
				notify1 = true;
			}
			
		});		
	}		

	@Test
	public void testGetFlowListenersObject() throws FlowAnnotationException {		
		IFlowListener<String> listener;
		IFlowCarter<String> flowCarter = createFlowCarter();
		listener = annotationFactory.getFlowListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@SuppressWarnings("unused")
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}
			
		});
		listener.onFlowInitialized(flowCarter);
		assertFalse("Listener is not annotated.", notify1);
		clearResults();

		listener = annotationFactory.getFlowListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowEvents(event=FlowEvent.onFlowInitialized)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

			@FlowEvents(event=FlowEvent.onFlowFinished)
			public void notifier2(IFlowCarter<String> carter) {
				notify2 = true;
			}
			
		});
		listener.onFlowInitialized(flowCarter);
		assertTrue("Expected onFlowInitialized.", notify1);
		assertFalse("UNExpected onFlowFinished.", notify2);
		clearResults();
		listener.onFlowFinished(flowCarter);
		assertFalse("UNExpected onFlowInitialized.", notify1);
		assertTrue("Expected onFlowFinished.", notify2);
		clearResults();

		listener = annotationFactory.getFlowListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowEvents(event=FlowEvent.all)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

		});
		listener.onFlowInitialized(flowCarter);
		assertTrue("Expected onFlowInitialized.", notify1);
		assertTrue("Expected onFlowFinished.", notify2);
		clearResults();
		listener.onFlowInitialized(flowCarter);
		assertTrue("Expected onFlowInitialized.", notify1);
		assertTrue("Expected onFlowFinished.", notify2);

	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetStateChangeListenersObjectExcp1() throws FlowAnnotationException {
		annotationFactory.getStateChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateEvent
			public void notifier1() {
				notify1 = true;
			}
			
		});
	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetStateChangeListenersObjectExcp2() throws FlowAnnotationException {
		annotationFactory.getStateChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateEvent
			public void notifier1(String payload) {
				notify1 = true;
			}
			
		});
	}

	@Test
	public void testGetStateChangeListenersObject() throws FlowAnnotationException {
		IFlowStateChangeListener<String> listener;
		IFlowCarter<String> flowCarter = createFlowCarter();
		listener = annotationFactory.getStateChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@SuppressWarnings("unused")
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}
			
		});
		listener.onStateEntry(flowCarter, flowCarter.getCurrentState());
		assertFalse("Listener is not annotated.", notify1);
		clearResults();

		listener = annotationFactory.getStateChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateEvent(event=StateEvent.onStateEntry)
			public void notifier1(IFlowCarter<String> carter, IFlowState flowState) {
				notify1 = true;
			}

			@FlowStateEvent(event=StateEvent.onStateLeaving)
			public void notifier2(IFlowCarter<String> carter, IFlowState flowState) {
				notify2 = true;
			}
			
		});
		listener.onStateEntry(flowCarter, flowCarter.getCurrentState());
		assertTrue("Expected onStateEntry.", notify1);
		assertFalse("UNExpected onStateLeaving.", notify2);
		clearResults();
		listener.onStateLeaving(flowCarter, flowCarter.getCurrentState());
		assertFalse("UNExpected onStateEntry.", notify1);
		assertTrue("Expected onStateLeaving.", notify2);
		clearResults();

		listener = annotationFactory.getStateChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateEvent(event=StateEvent.all)
			public void notifier1(IFlowCarter<String> carter, IFlowState flowState) {
				notify1 = true;
			}

		});
		listener.onStateEntry(flowCarter, flowCarter.getCurrentState());
		assertTrue("Expected onStateEntry.", notify1);
		assertTrue("Expected onStateLeaving.", notify2);
		clearResults();
		listener.onStateLeaving(flowCarter, flowCarter.getCurrentState());
		assertTrue("Expected onStateEntry.", notify1);
		assertTrue("Expected onStateLeaving.", notify2);

	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetStateValidationListenersObjectExcp1() throws FlowAnnotationException {
		annotationFactory.getStateValidationListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateValidation
			public void notifier1() {
				notify1 = true;
			}
			
		});
	}
	
	@Test
	public void testGetStateValidationListenersObject() throws FlowAnnotationException {
		IFlowStateValidationListener<String> listener;
		IFlowCarter<String> flowCarter = createFlowCarter();
		listener = annotationFactory.getStateValidationListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@SuppressWarnings("unused")
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}
			
		});
		listener.onStateValid(flowCarter);
		assertFalse("Listener is not annotated.", notify1);
		clearResults();

		listener = annotationFactory.getStateValidationListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateValidation(event=ValidationEvent.valid)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

			@FlowStateValidation(event=ValidationEvent.invalid)
			public void notifier2(IFlowCarter<String> carter) {
				notify2 = true;
			}
			
		});
		listener.onStateValid(flowCarter);
		assertTrue("Expected onStateValid.", notify1);
		assertFalse("UNExpected onStateLeaving.", notify2);
		clearResults();
		listener.onStateInvalid(flowCarter);
		assertFalse("UNExpected onStateValid.", notify1);
		assertTrue("Expected onStateInvalid.", notify2);
		clearResults();

		listener = annotationFactory.getStateValidationListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateValidation(event=ValidationEvent.all)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

		});
		listener.onStateValid(flowCarter);
		assertTrue("Expected onStateValid.", notify1);
		assertTrue("Expected onStateInvalid.", notify2);
		clearResults();
		listener.onStateInvalid(flowCarter);
		assertTrue("Expected onStateValid.", notify1);
		assertTrue("Expected onStateInvalid.", notify2);

	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetTransitionChangeListenersObjectExcp1() throws FlowAnnotationException {
		annotationFactory.getTransitionChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowTransitionEvent
			public void notifier1() {
				notify1 = true;
			}
			
		});
	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetTransitionChangeListenersObjectExcp2() throws FlowAnnotationException {
		annotationFactory.getTransitionChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowTransitionEvent
			public void notifier1(String payload) {
				notify1 = true;
			}
			
		});
	}

	@Test
	public void testGetTransitionChangeListenersObject() throws FlowAnnotationException {
		IFlowTransitionChangeListener<String> listener;
		IFlowCarter<String> flowCarter = createFlowCarter();
		listener = annotationFactory.getTransitionChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@SuppressWarnings("unused")
			public void notifier1(IFlowCarter<String> carter, IFlowState flowState) {
				notify1 = true;
			}
			
		});
		listener.onTransitionStart(flowCarter, flowCarter.getNextTransition());
		assertFalse("Listener is not annotated.", notify1);
		clearResults();

		listener = annotationFactory.getTransitionChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowTransitionEvent(event=TransitionEvent.onTransitionStart)
			public void notifier1(IFlowCarter<String> carter, IFlowTransition flowTransition) {
				notify1 = true;
			}

			@FlowTransitionEvent(event=TransitionEvent.onTransitionFinished)
			public void notifier2(IFlowCarter<String> carter, IFlowTransition flowTransition) {
				notify2 = true;
			}
			
		});
		listener.onTransitionStart(flowCarter, flowCarter.getNextTransition());
		assertTrue("Expected onTransitionStart.", notify1);
		assertFalse("UNExpected onTransitionFinished.", notify2);
		clearResults();
		listener.onTransitionFinished(flowCarter, flowCarter.getNextTransition());
		assertFalse("UNExpected onTransitionStart.", notify1);
		assertTrue("Expected onTransitionFinished.", notify2);
		clearResults();

		listener = annotationFactory.getTransitionChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowTransitionEvent(event=TransitionEvent.all)
			public void notifier1(IFlowCarter<String> carter, IFlowTransition flowTransition) {
				notify1 = true;
			}

		});
		listener.onTransitionStart(flowCarter, flowCarter.getNextTransition());
		assertTrue("Expected onTransitionStart.", notify1);
		assertTrue("Expected onTransitionFinished.", notify2);
		clearResults();
		listener.onTransitionFinished(flowCarter, flowCarter.getNextTransition());
		assertTrue("Expected onTransitionStart.", notify1);
		assertTrue("Expected onTransitionFinished.", notify2);
	}
	
	@Test(expected=FlowAnnotationException.class)
	public void testGetFlowConditionProcessorsObjectExcp1() throws FlowAnnotationException {
		annotationFactory.getFlowConditionProcessors(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowConditionProcessor
			public void condition1() {
				notify1 = true;
			}
			
		});
	}

	public void testGetFlowConditionProcessorsObject1() throws FlowAnnotationException, CannotProcessConditionException {
		IFlowConditionProcessor<String> processor;
		IFlowCarter<String> flowCarter = createFlowCarter();
		notify1 = true;
		processor = annotationFactory.getFlowConditionProcessors(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowConditionProcessor
			public boolean condition1(String conditionExpression, IFlowCarter<String> carter) {
				return notify1;
			}
			
		});
		assertTrue(processor.ifCondition("expression1", flowCarter));
	}

	@Test(expected=CannotProcessConditionException.class)
	public void testGetFlowConditionProcessorsObject2() throws FlowAnnotationException, CannotProcessConditionException {
		IFlowConditionProcessor<String> processor;
		IFlowCarter<String> flowCarter = createFlowCarter();
		notify1 = true;
		processor = annotationFactory.getFlowConditionProcessors(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowConditionProcessor(conditionExpressionRegex="expression1")
			public boolean condition1(String conditionExpression, IFlowCarter<String> carter) {
				return notify1;
			}
			
		});
		assertTrue(processor.ifCondition("expression2", flowCarter));
	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetFlowSwitchProcessorsObjectExcp1() throws FlowAnnotationException {
		annotationFactory.getFlowSwitchProcessors(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowSwitchProcessorExpression
			public void notifier1() {
				notify1 = true;
			}
			
		});
	}

	@Test
	public void testGetFlowSwitchProcessorsObject() throws FlowAnnotationException {
		IFlowSwitchProcessor<String> processor;
		IFlowCarter<String> flowCarter = createFlowCarter();
		notify1 = true;
		processor = annotationFactory.getFlowSwitchProcessors(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowSwitchProcessorExpression(switchExpressionRegex="expression1")
			public String expression1(String conditionExpression, IFlowCarter<String> carter) {
				return "RESULT";
			}

		});
		assertEquals("RESULT", processor.resolveSwitchExpression(flowCarter, "expression1"));
		assertNotEquals("XX", processor.resolveSwitchExpression(flowCarter, "expression2"));
	}
	
}
