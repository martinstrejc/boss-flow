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

import java.io.Serializable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.annotation.FlowConditionProcessor;
import cz.wicketstuff.boss.flow.annotation.FlowEvents;
import cz.wicketstuff.boss.flow.annotation.FlowEvents.FlowEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent;
import cz.wicketstuff.boss.flow.annotation.FlowStateEvent.StateEvent;
import cz.wicketstuff.boss.flow.annotation.FlowSwitchProcessorExpression;
import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent;
import cz.wicketstuff.boss.flow.annotation.FlowTransitionEvent.TransitionEvent;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;
import cz.wicketstuff.boss.flow.processor.StateDataException;
import cz.wicketstuff.boss.flow.processor.ext.DefaultFlowProcessor;

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
		processor = new Processor1();
	}
	
	private void initProcessor() throws FlowException {
		processor.initializeProcessor();
	}

	
	private void testCompleteFlow() throws FlowException {
		testCompleteFlowSimpleWay();
		testCompleteFlowBank();
	}

	private void testCompleteFlowSimpleWay() throws FlowException {
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

	private void testCompleteFlowBank() throws FlowException {
		flow = processor.initFlow(new TestPayload());
		checkCurrentState(ST_ShowBasket);
		checkCurrentStateInitial();
		processor.invokeDefaultNextTransition(flow);
		checkCurrentState(ST_ChoosePayment);
		getPayload().setPaymentType(TestPaymentType.CASH);
		processor.invokeDefaultNextTransition(flow);
		checkCurrentState(ST_CashPayment);
		processor.invokeDefaultNextTransition(flow);
		checkCurrentState(ST_Overview);
		checkCurrentStateFinal();
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

	private TestPayload getPayload() {
		return flow.getPayload();
	}

	public static enum TestPaymentType {
		CASH,
		PAYPAL,
		CARD;
	} 
	

	public static class TestPayload implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private boolean paid;
		
		private TestPaymentType paymentType;

		/**
		 * @return the paid
		 */
		public boolean isPaid() {
			return paid;
		}

		/**
		 * @param paid the paid to set
		 */
		public void setPaid(boolean paid) {
			this.paid = paid;
		}

		/**
		 * @return the paymentType
		 */
		public TestPaymentType getPaymentType() {
			return paymentType;
		}

		/**
		 * @param paymentType the paymentType to set
		 */
		public void setPaymentType(TestPaymentType paymentType) {
			this.paymentType = paymentType;
		}
		
	}
	
	
	
	public static class Processor1 extends DefaultFlowProcessor<TestPayload> {

		private static final long serialVersionUID = 1L;
		
		private static final Logger log = LoggerFactory.getLogger(Processor1.class);

		public Processor1() {
			super();
			setFlowInputStream(getClass().getClassLoader().getResourceAsStream(FLOW_XML_FILE));
		}

		
		@FlowEvents(event=FlowEvent.onFlowInitialized)
		public void onFlowInit(IFlowCarter<TestPayload> carter) {
			log.trace("Flow initialized, setting default payload");
			if(carter.getPayload() == null) {
				carter.setPayload(new TestPayload());	
			}
		}

		@FlowTransitionEvent(event=TransitionEvent.onTransitionStart, priority=0)
		public void onTransitionBeforeStart(IFlowCarter<TestPayload> carter, IFlowTransition transition) {
			log.trace("Before transition start");
		}

		@FlowTransitionEvent(event=TransitionEvent.onTransitionStart, priority=10000)
		public void onTransitionAfterStart(IFlowCarter<TestPayload> carter, IFlowTransition transition) {
			log.trace("After transition start");
		}

		@FlowTransitionEvent(event=TransitionEvent.onTransitionStart, categoryNameRegex="flow.*", priority=10000)
		public void onTransitionAfterStartWithCategory(IFlowCarter<TestPayload> carter, IFlowTransition transition) {
			log.trace("After transition start");
		}

		@FlowTransitionEvent(event=TransitionEvent.onTransitionStart, priority=100)
		public void onTransition(IFlowCarter<TestPayload> carter, IFlowTransition transition) {
			log.trace("DO SOMETHING");
			Object o = carter.getStateData();
			if(o != null) {
				log.debug("State data: {}", o.toString());
			}
		}
		
		@FlowStateEvent(priority=1)
		public void onState(IFlowCarter<TestPayload> carter, IFlowState state) {
			log.trace("State started or finished");
		}

		@FlowStateEvent(event=StateEvent.onStateEntry, stateNameRegex="ChooseP.*", priority=1)
		public void onChoosePayment(IFlowCarter<TestPayload> carter, IFlowState state) {
			carter.getPayload().setPaid(false);
		}

		@FlowStateEvent(event=StateEvent.onStateLeaving, stateNameRegex="ChooseP.*", priority=10)
		public void onCashPayment(IFlowCarter<TestPayload> carter, IFlowState state) {
			carter.getPayload().setPaid(true);
		}

		@FlowSwitchProcessorExpression(switchExpressionRegex="paymentDecision")
		public String paymentDecision(String condition, IFlowCarter<TestPayload> flowCarter) {
			TestPaymentType paymentType = flowCarter.getPayload().getPaymentType();
			return paymentType == null ? null :paymentType.name();
		}

		@FlowConditionProcessor(conditionExpressionRegex="isPaymentSuccessful")
		public boolean isPaymentSuccessful(String condition, IFlowCarter<TestPayload> flowCarter) {
			return flowCarter.getPayload().isPaid();
		}
		
		// TEMPORARY DATA
		
		@Override
		public Serializable createStateData(IFlowCarter<TestPayload> flowCarter, IFlowState flowState)
				throws NoSuchStateException, StateDataException {
			log.trace("createStateData");
			if(flowState == null) {
				return null;
			}
			if("CardPayment".equals(flowState.getStateName())) {
				log.trace("create CardPayment state data");
				return "";
			}
			return super.createStateData(flowCarter, flowState);
		}
		
		
		// INVOKE SCANNING ANNOTATED PROCESSORS AND LISTENERS
		
		@Override
		public Object defaultFlowListenerBean() {
			return this;
		}
		

		@Override
		public Object defaultConditionBean() {
			return this;
		}

		@Override
		public Object defaultSwitchBean() {
			return this;
		}

		@Override
		public Object defaultStateListenerBean() {
			return this;
		}

		@Override
		public Object defaultTransitionListenerBean() {
			return this;
		}
		
		
	}

}
