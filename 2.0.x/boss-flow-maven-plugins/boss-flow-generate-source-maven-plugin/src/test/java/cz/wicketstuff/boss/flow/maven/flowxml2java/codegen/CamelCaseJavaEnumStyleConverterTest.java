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
public class CamelCaseJavaEnumStyleConverterTest {

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.CamelCaseJavaEnumStyleConverter#UpperCaseJavaStyleConverter()}.
	 */
	@Test
	public void testUpperCaseJavaStyleConverter() {
		assertNotNull(new CamelCaseJavaEnumStyleConverter());
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.CamelCaseJavaEnumStyleConverter#createJavaStyleName(java.lang.String)}.
	 */
	@Test
	public void testCreateJavaStyleName() {
		CamelCaseJavaEnumStyleConverter converter = new CamelCaseJavaEnumStyleConverter();
		assertEquals("ANY_THINK_ANY_WHERE_IS_123_", converter.createJavaStyleName("AnyThink   AnyWhere-is  123 !-"));
		assertEquals("_", converter.createJavaStyleName("      "));
		
	}

}
