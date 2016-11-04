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
package cz.wicketstuff.boss.flow.processor.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.wicketstuff.boss.flow.model.basic.FlowState;

/**
 * @author Martin Strejc
 *
 */
public class DefaultFlowStateOrdinalComparatorTest {

	private DefaultFlowStateOrdinalComparator comparator = new DefaultFlowStateOrdinalComparator();
	
	@Test
	public void testCompare() {
		FlowState state = new FlowState();
		assertEquals("States are ordinally the same", 0, comparator.compare(null, null));
		assertEquals("States are ordinally the same", 0, comparator.compare(state, null));
		assertEquals("States are ordinally the same", 0, comparator.compare(null, state));
	}
	
	@Test
	public void testCompare2() {
		FlowState state = new FlowState();
		state.setOrder(10);
		assertTrue("The first state is ordinally less than the second", comparator.compare(null, state) < 0);
		assertTrue("The first state is ordinally grater than the second", comparator.compare(state, null) > 0);
	}

	@Test
	public void testCompare3() {
		FlowState state1 = new FlowState();
		state1.setOrder(5);
		FlowState state2 = new FlowState();
		state2.setOrder(10);
		assertTrue("The first state is ordinally less than the second", comparator.compare(state1, state2) < 0);
		assertTrue("The first state is ordinally grater than the second", comparator.compare(state2, state1) > 0);
		state1.setOrder(10);
		assertEquals("States are ordinally the same", 0, comparator.compare(state1, state2));
		assertEquals("States are ordinally the same", 0, comparator.compare(state2, state1));
	}

}
