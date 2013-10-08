package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Martin Strejc
 *
 */
public class FlowCodeGeneratorTest {

//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#FlowCodeGenerator()}.
//	 */
//	@Test
//	public void testFlowCodeGenerator() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#generate(cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml, cz.wicketstuff.boss.flow.builder.xml.FlowBuilderCarter)}.
//	 */
//	@Test
//	public void testGenerate() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#fillEnumMap(java.util.Map)}.
//	 */
//	@Test
//	public void testFillEnumMap() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#javaStyleNames(java.util.List)}.
	 */
	@Test
	public void testJavaStyleNames() {
		List<EnumDescriptor> list = new ArrayList<>(10);
		FlowCodeGenerator generator = new FlowCodeGenerator();
		list.add(new EnumDescriptor("myName 1"));
		list.add(new EnumDescriptor("xxName 1"));
		list.add(new EnumDescriptor("MyName 1"));
		generator.javaStyleNames(list);
		assertEquals("MYNAME_1", list.get(0).getName());
		assertEquals("XXNAME_1", list.get(1).getName());
		assertEquals("MYNAME_1_01", list.get(2).getName());
	}
	
	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#appendNameSuffix(String, Integer)}.
	 */
	@Test
	public void testAppendNameSuffix() {
		FlowCodeGenerator generator = new FlowCodeGenerator();
		assertEquals("my_test", generator.appendNameSuffix("my_test", null));
		assertEquals("my_test_00", generator.appendNameSuffix("my_test", 0));
		assertEquals("my_test_01", generator.appendNameSuffix("my_test", 1));
		assertEquals("my_test_09", generator.appendNameSuffix("my_test", 9));
		assertEquals("my_test_10", generator.appendNameSuffix("my_test", 10));
		assertEquals("my_test_99", generator.appendNameSuffix("my_test", 99));
		assertEquals("my_test_100", generator.appendNameSuffix("my_test", 100));
		assertEquals("my_test__00", generator.appendNameSuffix("my_test_", 0));
		assertEquals("my_test__99", generator.appendNameSuffix("my_test_", 99));
	}
	

//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#buildFlow(java.io.File)}.
//	 */
//	@Test
//	public void testBuildFlowFile() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#buildFlow(java.io.InputStream)}.
//	 */
//	@Test
//	public void testBuildFlowInputStream() {
//		fail("Not yet implemented");
//	}

}
