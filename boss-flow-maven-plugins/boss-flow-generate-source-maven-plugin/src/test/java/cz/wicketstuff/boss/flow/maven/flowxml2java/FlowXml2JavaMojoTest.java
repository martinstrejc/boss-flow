/**
 * 
 */
package cz.wicketstuff.boss.flow.maven.flowxml2java;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Martin Strejc
 *
 */
public class FlowXml2JavaMojoTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml2JavaMojo#FlowXml2JavaMojo()}.
//	 */
//	@Test
//	public void testFlowXml2JavaMojo() {
//		fail("Not yet implemented");
//	}

//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml2JavaMojo#execute()}.
//	 */
//	@Test
//	public void testExecute() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml2JavaMojo#generate(cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml)}.
	 * @throws MojoFailureException 
	 * @throws MojoExecutionException 
	 */
	@Test
	public void testGenerate() throws MojoExecutionException, MojoFailureException {
		FlowXml flowXml = new FlowXml();
		flowXml.id = "test1";
		flowXml.xmlFile = new File("/");
		flowXml.packageName = "xml.generated";
		flowXml.stateEnumName = "FlowStateEnum";
		flowXml.transitionEnumName = "FlowTransitionEnum";
		FlowXml2JavaMojo plugin = new FlowXml2JavaMojo();
		// plugin.generate(flowXml);
	}

//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml2JavaMojo#checkParameters(cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml)}.
//	 */
//	@Test
//	public void testCheckParameters() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml2JavaMojo#error(java.lang.String)}.
//	 */
//	@Test
//	public void testError() {
//		fail("Not yet implemented");
//	}

}
