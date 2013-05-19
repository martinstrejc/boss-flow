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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;
import cz.wicketstuff.boss.flow.test.FlowFileResource;

/**
 * Test creating flow tree from XML configuration files using JAXB.
 * 
 * @author Martin Strejc
 *
 */
public class JaxbFlowBuilderTest {
	
	private Logger log = LoggerFactory.getLogger(getClass().getName());
	
	private FlowFileResource resourceHelper;
	private JaxbFlowBuilder builder;
	
	
	@Before
	public void setUp() throws Exception {
		resourceHelper = new FlowFileResource();
		builder = JaxbFlowBuilder.newInstance();
	}
	
	@Test
	public void testNewInstance() {
		assertNotNull("Instance of JaxbFlowBuilder.newInstance() cannot be null", builder);
		assertTrue("Instance of JaxbFlowBuilder.newInstance() has to be the same type", builder instanceof JaxbFlowBuilder);
	}

	@Test
	public void testBuildFlowInputStream() {
		try {
			builder.buildFlow(resourceHelper.getCompleteFlowFileStream());
		} catch (FlowException e) {
			log.error("Parsing failed", e);
			fail("Parsing '" + resourceHelper.getCompleteFlowFileName() + "' failed: " + e.getMessage());
		}
	}

	@Test(expected=NoSuchStateException.class)
	public void testBuildFlowInputStreamMissingState() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowMissingStateFileStream());
		log.error("Parsing failed, a missing state must be detected!");
		fail("Parsing '" + resourceHelper.getFlowMissingStateFileName() + "' failed, a missing state must be detected!");
	}
	
	@Test(expected=NoSuchTransitionException.class)
	public void testBuildFlowInputStreamMissingTransition() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowMissingTransitionFileStream());
		log.error("Parsing failed, a missing transition must be detected!");
		fail("Parsing '" + resourceHelper.getFlowMissingTransitionFileName() + "' failed, a missing transition must be detected!");
	}

	@Test(expected=DuplicatedStateException.class)
	public void testBuildFlowInputStreamDuplicatedState() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowDuplicatedStateFileStream());
		log.error("Parsing failed, a duplicated state must be detected!");
		fail("Parsing '" + resourceHelper.getFlowDuplicatedStateFileName() + "' failed, a duplicated state must be detected!");
	}
	
	@Test(expected=DuplicatedTransitionException.class)
	public void testBuildFlowInputStreamDuplicatedTransition() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowDuplicatedTransitionFileStream());
		log.error("Parsing failed, a duplicated transition must be detected!");
		fail("Parsing '" + resourceHelper.getFlowDuplicatedTransitionFileName() + "' failed, a duplicated transition must be detected!");
	}

	@Test(expected=InvalidFlowAttributeException.class)
	public void testBuildFlowInputStreamInvalidPrevNextTransition1() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowInvalidPrevNext1FileStream());
		log.error("Parsing failed, wrong default previous and next transition attributes must be detected!");
		fail("Parsing '" + resourceHelper.getFlowInvalidPrevNext1FileName() + "' failed, wrong default previous and next transition attributes must be detected!");
	}

	@Test(expected=InvalidFlowAttributeException.class)
	public void testBuildFlowInputStreamInvalidPrevNextTransition2() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowInvalidPrevNext2FileStream());
		log.error("Parsing failed, wrong default previous and next transition attributes must be detected!");
		fail("Parsing '" + resourceHelper.getFlowInvalidPrevNext2FileName() + "' failed, wrong default previous and next transition attributes be detected!");
	}

	@Test(expected=InvalidFlowAttributeException.class)
	public void testBuildFlowInputStreamInvalidPrevNextTransition3() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowInvalidPrevNext3FileStream());
		log.error("Parsing failed, wrong default previous and next transition attributes must be detected!");
		fail("Parsing '" + resourceHelper.getFlowInvalidPrevNext3FileName() + "' failed, wrong default previous and next transition attributes must be detected!");
	}

	@After
	public void tearDown() throws Exception {
		builder = null;
		resourceHelper = null;
	}

}
