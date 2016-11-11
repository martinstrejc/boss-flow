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
package cz.wicketstuff.boss.flow.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.basic.FlowCarter;
import cz.wicketstuff.boss.flow.processor.FlowAlreadyFinishedException;
import cz.wicketstuff.boss.flow.processor.FlowInitializationException;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;
import cz.wicketstuff.boss.flow.processor.UnsupportedStateOperationException;

public abstract class AbstractFlowStepTest extends AbstractFlowTest {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Before
	public void setUp() throws FlowException {
		processor = createProcessor();
		carter = null;		
	}

	@After
	public void tearDown() {
		processor = null;
		carter = null;		
	}
	
	public AbstractFlowStepTest() {
		super();
	}
	
	
	abstract protected IFlowProcessor<String> createProcessor() throws FlowException;

	protected void initializeCarter() throws FlowException {
		carter = null;
		carter = processor.initFlow(flowId, payload, S0initialState);
	}
	
	@Test
	public void testInitialization() throws FlowException {
		log.trace("Test flow initilization.");
		initializeCarter();
		assertNotNull("Carter initialization cannot be null", carter);
		assertEquals("FlowId must be the same value whole the time", (long)flowId, (long)carter.getFlowProcessId());
		assertEquals("Payload must be store in the flow carter.", payload, carter.getPayload());		
		log.trace("Test initial state.");
		checkCurrentState(S0initialState);
	}

	@Test
	public void testIfTrueToFinal() throws FlowException {
		log.trace("Test if true to final.");
		initializeCarter();
		checkCurrentState(S0initialState);	
		processor.invokeTransition(carter, T01);
		checkCurrentState(S1realState);	
		setIfExpressionResult(true);
		processor.invokeTransition(carter, T12);
		checkCurrentState(S3viewState);	
		// processor.invokeTransition(carter, T38);
		processor.invokeDefaultPreviousTransition(carter);
		checkCurrentState(S9finalState);	
	}

	@Test(expected=FlowAlreadyFinishedException.class)
	public void testAfterFinal() throws FlowException {
		log.trace("Test if true to final and after final.");
		initializeCarter();
		checkCurrentState(S0initialState);	
		processor.invokeTransition(carter, T01);
		checkCurrentState(S1realState);	
		setIfExpressionResult(true);
		// processor.invokeTransition(carter, T12);
		processor.invokeDefaultNextTransition(carter);
		checkCurrentState(S3viewState);	
		processor.invokeTransition(carter, T38);
		checkCurrentState(S9finalState);	
		log.trace("Test after final.");
		processor.invokeTransition(carter, T96);
		fail("No transaction cannot be invoked when flow is in a final state!");
	}

	@Test(expected=NoSuchTransitionException.class)
	public void testIncorrectState1() throws FlowException {
		log.trace("Test if true to final and after final.");
		initializeCarter();
		checkCurrentState(S0initialState);	
		processor.invokeDefaultNextTransition(carter);
		fail("Next transaction cannot be invoked here!");
	}

	@Test(expected=UnsupportedStateOperationException.class)
	public void testIncorrectState2() throws FlowException {
		log.trace("Test if true to final and after final.");
		initializeCarter();
		checkCurrentState(S0initialState);	
		processor.invokeTransition(carter, T01);
		checkCurrentState(S1realState);	
		setIfExpressionResult(true);
		processor.invokeTransition(carter, T12);
		FlowCarter<?> fc = (FlowCarter<?>)carter;
		fc.setCurrentState(fc.getPreviousState());
		log.debug("CURRENT: " + fc.getCurrentState());
		processor.invokeDefaultNextTransition(carter);
		fail("Exception hasn't been thrown!");
	}

	@Test
	public void testSwitchDefaultIfFalse() throws FlowException {
		log.trace("Test switch condition default.");
		initializeCarter();
		checkCurrentState(S0initialState);	
		processor.invokeTransition(carter, T01);
		checkCurrentState(S1realState);	
		setIfExpressionResult(false);
		setSwitchExpressionResult(null);
		processor.invokeTransition(carter, T15);
		checkCurrentState(S4viewState);	
		log.trace("Test switch condition default: " + CASE_toViewS6);
		setSwitchExpressionResult(CASE_toViewS6);
		processor.invokeTransition(carter, T45);
		checkCurrentState(S6viewStateInitial);	
		processor.invokeTransition(carter, T68);
		checkCurrentState(S9finalState);	
	}

	@Test
	public void testSwitchToFinal() throws FlowException {
		log.trace("Test switch condition default: " + CASE_toFinal);
		initializeCarter();
		checkCurrentState(S0initialState);	
		processor.invokeTransition(carter, T01);
		checkCurrentState(S1realState);	
		setIfExpressionResult(false);
		setSwitchExpressionResult(null);
		setSwitchExpressionResult(CASE_toFinal);
		processor.invokeTransition(carter, T15);
		checkCurrentState(S9finalState);	
	}

	@Test
	public void testSecondaryInitialState() throws FlowException {
		log.trace("Test secondary initial state: " + S6viewStateInitial);
		carter = null;
		carter = processor.initFlow(flowId, payload, S6viewStateInitial);
		checkCurrentState(S6viewStateInitial);	
		processor.invokeTransition(carter, T68);
		checkCurrentState(S9finalState);	
	}

	@Test(expected=FlowInitializationException.class)
	public void testWrongInitialState() throws FlowException {
		log.trace("Test wrong initial state: " + S3viewState);
		carter = null;
		carter = processor.initFlow(flowId, payload, S3viewState);
		fail("Cannot init flow from step that is not an initial step.");
	}

}
