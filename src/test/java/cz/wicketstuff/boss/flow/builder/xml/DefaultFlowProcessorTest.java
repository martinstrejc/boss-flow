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
package cz.wicketstuff.boss.flow.builder.xml;

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
 * @author Martin Strejc
 *
 */
public class DefaultFlowProcessorTest extends AbstractFlowStepTest {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	private final String STATE_DATA = "test state data";
	
	public DefaultFlowProcessorTest() {
		super();
		log.trace("Test complete flow builded by JAXB.");
	}

	@Override
	protected IFlowProcessor<String> createProcessor() throws FlowException {
		log.trace("Creating flow processor");
		DefaultFlowProcessor<String> processor = new DefaultFlowProcessor<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public IFlowStateProcessor<String> defaultFlowStateProcessor(IFlowTree flowTree) {
				return createFlowStateProcessor();
			}
			
		};
		FlowFileResource resourceHelper = new FlowFileResource();
		processor.setFlowInputStream(resourceHelper.getCompleteFlowFileStream());
		processor.setDefaultInitialStateName(S0initialState);
		processor.setStateDataFactory(new IFlowStateDataFactory() {
			
			@Override
			public Serializable createStateData(IFlowState flowState)
					throws NoSuchStateException, StateDataException {
				log.trace("Creating data for " + flowState.getStateName());
				return STATE_DATA;
			}
		});
		// not applicable here
		// processor.scanAnnotedBeans(this, this, this, this);
		return processor.initializeProcessor();
	}
	
	@Test
	public void testCompareStatesOrdinalityState() throws FlowException {
		DefaultFlowProcessor<String> p = (DefaultFlowProcessor<String>)processor;
		IFlowState stateNull = p.getStateResolver().resolveState(S2ifState);
		IFlowState state1 = p.getStateResolver().resolveState(S1realState);
		IFlowState state2 = p.getStateResolver().resolveState(S3viewState);
		assertTrue("The first state is ordinally less than the second", p.compareStatesOrdinality(state1, state2) < 0);
		assertTrue("The first state is ordinally grater than the second", p.compareStatesOrdinality(state2, state1) > 0);
		assertTrue("The first state is ordinally less than the second", p.compareStatesOrdinality(stateNull, state2) < 0);
		assertTrue("The first state is ordinally grater than the second", p.compareStatesOrdinality(state2, stateNull) > 0);
		assertEquals("States are ordinally the same", 0, p.compareStatesOrdinality(state1, state1));

	}
	
	@Test
	public void testCreateStateData() throws FlowException {
		DefaultFlowProcessor<String> p = (DefaultFlowProcessor<String>)processor;
		IFlowCarter<String> flow = processor.initFlow(1L);
		assertEquals(STATE_DATA, flow.getStateData());
		p.invokeTransition(flow, "t01");
		assertEquals(STATE_DATA, flow.getStateData());
		p.invokeTransition(flow, "t12");
		assertEquals(null, flow.getStateData());
	}


}
