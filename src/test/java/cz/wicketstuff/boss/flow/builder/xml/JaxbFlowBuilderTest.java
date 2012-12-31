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

	@Test(expected=DuplicateStateException.class)
	public void testBuildFlowInputStreamDuplicatedState() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowDuplicatedStateFileStream());
		log.error("Parsing failed, a duplicated state must be detected!");
		fail("Parsing '" + resourceHelper.getFlowDuplicatedStateFileName() + "' failed, a duplicated state must be detected!");
	}
	
	@Test(expected=DuplicateTransitionException.class)
	public void testBuildFlowInputStreamDuplicatedTransition() throws FlowException {
		builder.buildFlow(resourceHelper.getFlowDuplicatedTransitionFileStream());
		log.error("Parsing failed, a duplicated transition must be detected!");
		fail("Parsing '" + resourceHelper.getFlowDuplicatedTransitionFileName() + "' failed, a duplicated transition must be detected!");
	}

	@After
	public void tearDown() throws Exception {
		builder = null;
		resourceHelper = null;
	}

}
