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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;


import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.basic.SimpleFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.condition.FlowIfConditionException;
import cz.wicketstuff.boss.flow.processor.condition.IFlowConditionProcessor;
import cz.wicketstuff.boss.flow.processor.condition.IFlowSwitchProcessor;

public abstract class AbstractFlowTest implements ICompleteFlowTest {

	public final String payload = "test payload";
	public final long flowId = 17L;
	
	private boolean ifExpressionResult = false;
	private String switchExpressionResult = null;
	
	protected IFlowProcessor<String> processor;
	
	protected IFlowCarter<String> carter;
	
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
	
	public AbstractFlowTest() {
		super();
	}
	
	
	abstract protected IFlowProcessor<String> createProcessor() throws FlowException;

	protected void initializeCarter() throws FlowException {
		carter = null;
		// test without initial, testing default
		// carter = processor.initFlow(flowId, payload, S0initialState);
		carter = processor.initFlow(flowId, payload);
	}
	
	public IFlowProcessor<String> getProcessor() {
		return processor;
	}

	public void setProcessor(IFlowProcessor<String> processor) {
		this.processor = processor;
	}

	public IFlowCarter<String> getCarter() {
		return carter;
	}

	public void setCarter(IFlowCarter<String> carter) {
		this.carter = carter;
	}
	
	public IFlowState getCurrentState() {
		return getCarter().getCurrentState();
	}
	
	public boolean isIfExpressionResult() {
		return ifExpressionResult;
	}

	public void setIfExpressionResult(boolean ifExpressionResult) {
		this.ifExpressionResult = ifExpressionResult;
	}

	public String getSwitchExpressionResult() {
		return switchExpressionResult;
	}

	public void setSwitchExpressionResult(String switchExpressionResult) {
		this.switchExpressionResult = switchExpressionResult;
	}

	protected void checkCurrentState(String stateName) {
		assertEquals("Wrong current state of the flow", stateName, getCurrentState().getStateName());
	}
	
	protected IFlowStateProcessor<String> createFlowStateProcessor() {
		SimpleFlowStateProcessor<String> sp = new SimpleFlowStateProcessor<String>();
		sp.setConditionProcessor(new IFlowConditionProcessor<String>() {
			
			@Override
			public boolean ifCondition(String conditionExpression,
					IFlowCarter<String> flow) throws FlowIfConditionException {
				return isIfExpressionResult();
			}
		});
		sp.setSwitchProcessor(new IFlowSwitchProcessor<String>() {
			
			@Override
			public String resolveSwitchExpression(IFlowCarter<String> flow,
					String switchExpression) {
				return getSwitchExpressionResult();
			}
		});
		return sp;
	}	
	
}