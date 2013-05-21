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

import static org.junit.Assert.*;

import java.beans.Transient;
import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.wicketstuff.boss.flow.annotation.FlowConditionProcessor;
import cz.wicketstuff.boss.flow.annotation.FlowEvents;
import cz.wicketstuff.boss.flow.annotation.FlowStateValidation;
import cz.wicketstuff.boss.flow.annotation.FlowSwitchProcessorExpression;
import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent;
import cz.wicketstuff.boss.flow.annotation.FlowEvents.FlowEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent.StateEvent;
import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent.TransitionEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.basic.FlowCarter;
import cz.wicketstuff.boss.flow.model.basic.FlowState;
import cz.wicketstuff.boss.flow.model.basic.FlowTransition;
import cz.wicketstuff.boss.flow.processor.IFlowListener;
import cz.wicketstuff.boss.flow.processor.IFlowStateChangeListener;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionChangeListener;

/**
 * @author Martin Strejc
 *
 */
public class AnnotationFlowFactoryTest {

	private AnnotationFlowFactory<String> annotationFactory = new AnnotationFlowFactory<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	private boolean notify1;
	private boolean notify2;
	private boolean notify3;
	

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
		listener.onStateEntry(flowCarter);
		assertFalse("Listener is not annotated.", notify1);
		clearResults();

		listener = annotationFactory.getStateChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowStateEvent(event=StateEvent.onStateEntry)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

			@FlowStateEvent(event=StateEvent.onStateLeaving)
			public void notifier2(IFlowCarter<String> carter) {
				notify2 = true;
			}
			
		});
		listener.onStateEntry(flowCarter);
		assertTrue("Expected onStateEntry.", notify1);
		assertFalse("UNExpected onStateLeaving.", notify2);
		clearResults();
		listener.onStateLeaving(flowCarter);
		assertFalse("UNExpected onStateEntry.", notify1);
		assertTrue("Expected onStateLeaving.", notify2);
		clearResults();

		listener = annotationFactory.getStateChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowEvents(event=FlowEvent.all)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

		});
		listener.onStateEntry(flowCarter);
		assertTrue("Expected onStateEntry.", notify1);
		assertTrue("Expected onStateLeaving.", notify2);
		clearResults();
		listener.onStateLeaving(flowCarter);
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
		fail("Not yet implemented"); // TODO
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
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}
			
		});
		listener.onTransitionStart(flowCarter);
		assertFalse("Listener is not annotated.", notify1);
		clearResults();

		listener = annotationFactory.getTransitionChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowTransitionEvent(event=TransitionEvent.onTransitionStart)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

			@FlowTransitionEvent(event=TransitionEvent.onTransitionFinished)
			public void notifier2(IFlowCarter<String> carter) {
				notify2 = true;
			}
			
		});
		listener.onTransitionStart(flowCarter);
		assertTrue("Expected onTransitionStart.", notify1);
		assertFalse("UNExpected onTransitionFinished.", notify2);
		clearResults();
		listener.onTransitionFinished(flowCarter);
		assertFalse("UNExpected onTransitionStart.", notify1);
		assertTrue("Expected onTransitionFinished.", notify2);
		clearResults();

		listener = annotationFactory.getTransitionChangeListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowTransitionEvent(event=TransitionEvent.all)
			public void notifier1(IFlowCarter<String> carter) {
				notify1 = true;
			}

		});
		listener.onTransitionStart(flowCarter);
		assertTrue("Expected onTransitionStart.", notify1);
		assertTrue("Expected onTransitionFinished.", notify2);
		clearResults();
		listener.onTransitionFinished(flowCarter);
		assertTrue("Expected onTransitionStart.", notify1);
		assertTrue("Expected onTransitionFinished.", notify2);
	}
	
	@Test(expected=FlowAnnotationException.class)
	public void testGetFlowConditionProcessorsObjectExcp1() throws FlowAnnotationException {
		annotationFactory.getFlowConditionProcessors(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowConditionProcessor
			public void notifier1() {
				notify1 = true;
			}
			
		});
	}

	public void testGetFlowConditionProcessorsObject() throws FlowAnnotationException {
		fail("Not yet implemented"); // TODO
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
		fail("Not yet implemented"); // TODO
	}
	
//	@Test
//	public void testFindMethodCandidates() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testFindMethodConditionCandidates() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testFindMethodSwitchCandidates() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testCheckFlowMethod() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testCheckConditionMethod() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testCheckSwitchMethod() {
//		fail("Not yet implemented"); // TODO
//	}

}
