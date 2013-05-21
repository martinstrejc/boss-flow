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

import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.wicketstuff.boss.flow.annotation.FlowEvents;
import cz.wicketstuff.boss.flow.annotation.FlowEvents.FlowEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.basic.FlowCarter;
import cz.wicketstuff.boss.flow.processor.IFlowListener;

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

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=FlowAnnotationException.class)
	public void testGetFlowListenersObjectExcp() throws FlowAnnotationException {
		annotationFactory.getFlowListeners(new Serializable() {

			private static final long serialVersionUID = 1L;
		
			@FlowEvents
			public void notifier1() {
				notify1 = true;
			}
			
		});		
	}		

	@Test
	public void testGetFlowListenersObject() throws FlowAnnotationException {		
		IFlowListener<String> listener;
		IFlowCarter<String> flowCarter = new FlowCarter<String>();
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

	@Test
	public void testGetStateChangeListenersObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetStateValidationListenersObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetTransitionChangeListenersObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetFlowConditionProcessorsObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetFlowSwitchProcessorsObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testFindMethodCandidates() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testFindMethodConditionCandidates() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testFindMethodSwitchCandidates() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCheckFlowMethod() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCheckConditionMethod() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCheckSwitchMethod() {
		fail("Not yet implemented"); // TODO
	}

}
