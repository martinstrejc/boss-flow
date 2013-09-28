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
package cz.wicketstuff.boss.flow.complete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStateDataFactory;
import cz.wicketstuff.boss.flow.processor.IFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;
import cz.wicketstuff.boss.flow.processor.StateDataException;
import cz.wicketstuff.boss.flow.processor.ext.DefaultFlowProcessor;
import cz.wicketstuff.boss.flow.test.AbstractFlowStepTest;
import cz.wicketstuff.boss.flow.test.FlowFileResource;

/**
 * Complete test of all flow functionality
 * 
 * @author Martin Strejc
 *
 */
public class CompleteFlowProcessorTest {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	public static final String FLOW_XML_FILE = "test-full-flow.xml";
	
	public static final String ST_ShowBasket = "ShowBasket";
	public static final String ST_ChoosePayment = "ChoosePayment";
	public static final String ST_CashPayment = "CashPayment";
	public static final String ST_PaypalPayment = "PaypalPayment";
	public static final String ST_CardPayment = "CardPayment";
	public static final String ST_Overview = "Overview";
	public static final String ST_Cancelled = "Cancelled";
	public static final String ST_SuccessfulPaymentCondition = "SuccessfulPaymentCondition";
	public static final String ST_SwitchPaymentType = "SwitchPaymentType";
	public static final String ST_JoinFinishedPayment = "JoinFinishedPayment";
	
	public static final String SW_CASH = "CASH";
	public static final String SW_PAYPAL = "PAYPAL";
	public static final String SW_CARD = "CARD";
	
	public static final String TR_toShowBasket = "toShowBasket";
	public static final String TR_toChoosePayment = "toChoosePayment";
	public static final String TR_backToChoosePayment = "backToChoosePayment";
	public static final String TR_toCashPayment = "toCashPayment";
	public static final String TR_toPaypalPayment = "toPaypalPayment";
	public static final String TR_toCardPayment = "toCardPayment";
	public static final String TR_toOverview = "toOverview";
	public static final String TR_toSuccessfulPaymentCondition = "toSuccessfulPaymentCondition";
	public static final String TR_toSwitchPaymentType = "toSwitchPaymentType";
	public static final String TR_toJoinFinishedPayment = "toJoinFinishedPayment";
	public static final String TR_toCancelled = "toCancelled";
	
	
	
	private DefaultFlowProcessor<TestPayload> processor; 
	private IFlowCarter<TestPayload> flow;
	
	public CompleteFlowProcessorTest() {
		super();
		log.trace("Test complete flow builder and j.");
	}
	
	@Test
	public void testSimpleComplete() throws FlowException {
		prepareProcessor();
		initProcessor();
		testCompleteFlow();
	}

	private void prepareProcessor() throws FlowException {
		log.trace("Prepare processor");
		processor = new DefaultFlowProcessor<TestPayload>();
		processor.setFlowInputStream(getClass().getClassLoader().getResourceAsStream(FLOW_XML_FILE));
	}
	
	private void initProcessor() throws FlowException {
		log.trace("Initialize processor");
		processor.initializeProcessor();
	}

	
	private void testCompleteFlow() throws FlowException {
		flow = processor.initFlow(new TestPayload());
		checkCurrentState(ST_ShowBasket);
		checkCurrentStateInitial();
		processor.invokeTransition(flow, TR_toChoosePayment);
		checkCurrentState(ST_ChoosePayment);
		processor.invokeDefaultPreviousTransition(flow);
		checkCurrentState(ST_ShowBasket);
		checkCurrentStateInitial();
		processor.invokeDefaultNextTransition(flow);
		checkCurrentState(ST_ChoosePayment);
		processor.invokeDefaultNextTransition(flow);
		checkCurrentState(ST_ChoosePayment);
		processor.invokeTransition(flow, TR_toCancelled);
		checkCurrentState(ST_Cancelled);
		checkCurrentStateFinal();
	}
	
	
	private static class TestPayload implements Serializable {

		private static final long serialVersionUID = 1L;
	
		
	}
	
	private void checkCurrentState(String stateName) {
		assertEquals("State name doesn't match", stateName, flow.getCurrentState().getStateName());
	}

	private void checkCurrentStateInitial() {
		assertEquals("State is not initial", true, flow.getCurrentState().isInitialState());
	}

	private void checkCurrentStateFinal() {
		assertEquals("State is not final", true, flow.getCurrentState().isFinalState());
	}
}
