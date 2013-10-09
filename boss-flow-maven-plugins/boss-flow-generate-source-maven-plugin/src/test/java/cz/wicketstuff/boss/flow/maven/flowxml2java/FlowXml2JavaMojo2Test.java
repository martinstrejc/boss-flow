/**
 * 
 */
package cz.wicketstuff.boss.flow.maven.flowxml2java;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Martin Strejc
 *
 */
public class FlowXml2JavaMojo2Test extends AbstractMojoTestCase {

//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//	}


	@Test
	public void testExecution() throws Exception {
		File pom = getTestFile( "src/test/resources/test-generate-pom-01.xml" );
        assertNotNull(pom);
        assertTrue(pom.exists());

        FlowXml2JavaMojo mojo = (FlowXml2JavaMojo) lookupMojo("generate-sources", pom);
        assertNotNull(mojo);
        mojo.execute();
	}


}
