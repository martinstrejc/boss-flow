/**
 * 
 */
package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Martin Strejc
 *
 */
public class JavaCodeHelperTest {

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.JavaCodeHelper#checkJavaPackageName(java.lang.String)}.
	 */
	@Test
	public void testCheckJavaPackageName() {
		assertTrue(JavaCodeHelper.checkJavaClassName("com.example.mypackage1"));
		assertFalse(JavaCodeHelper.checkJavaClassName("com.example.mypackage."));
		assertFalse(JavaCodeHelper.checkJavaClassName(".com.example.my2package"));
		assertFalse(JavaCodeHelper.checkJavaClassName(".com.example.mypackage."));
		assertTrue(JavaCodeHelper.checkJavaClassName("com.example_package.my_package"));
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.JavaCodeHelper#checkJavaClassName(java.lang.String)}.
	 */
	@Test
	public void testCheckJavaClassName() {
		assertTrue(JavaCodeHelper.checkJavaClassName("MyExampleClass"));
		assertTrue(JavaCodeHelper.checkJavaClassName("_My_Example2Class"));
		assertFalse(JavaCodeHelper.checkJavaClassName("My_ExampleClass."));
		assertTrue(JavaCodeHelper.checkJavaClassName("my_ExampleClass1"));
		assertFalse(JavaCodeHelper.checkJavaClassName(".my_ExampleClass"));
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.JavaCodeHelper#entityNameToGetter(Class, String)}.
	 */
	@Test
	public void testEntityNameToGetter() {
		assertEquals("isMyIdent", JavaCodeHelper.entityNameToGetter(boolean.class, "myIdent"));
		assertEquals("getMyField", JavaCodeHelper.entityNameToGetter(Boolean.class, "myField"));
	}
	
}
