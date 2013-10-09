/**
 * 
 */
package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Martin Strejc
 *
 */
public class UpperCaseJavaStyleConverterTest {

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.UpperCaseJavaStyleConverter#UpperCaseJavaStyleConverter()}.
	 */
	@Test
	public void testUpperCaseJavaStyleConverter() {
		assertNotNull(new UpperCaseJavaStyleConverter());
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.UpperCaseJavaStyleConverter#createJavaStyleName(java.lang.String)}.
	 */
	@Test
	public void testCreateJavaStyleName() {
		UpperCaseJavaStyleConverter converter = new UpperCaseJavaStyleConverter();
		assertEquals("ANYTHINK_ANYWHERE_IS_123_", converter.createJavaStyleName("AnyThink   AnyWhere-is  123 !-"));
		assertEquals("_", converter.createJavaStyleName("      "));
	}

}
