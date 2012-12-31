package cz.wicketstuff.boss.flow.builder.xml;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
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
	public void testBuildFlowFlowDescriptorType() {
		// fail("Not yet implemented");
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

	@Test
	public void testBuildFlowTreeFlowBuilderCarterIntegerString() {
		//fail("Not yet implemented");
	}

	@Test
	public void testBuildFlowTreeInputStreamIntegerString() {
		//fail("Not yet implemented");
	}

	@After
	public void tearDown() throws Exception {
		builder = null;
	}

}
