package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.builder.xml.FlowBuilderCarter;
import cz.wicketstuff.boss.flow.builder.xml.StateCapsule;
import cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml;

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
//	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#generate(List)}.
//	 */
//	@Test
//	public void testGenerateList() {
//		List<FlowXml> flowXmls = new ArrayList<>(1);
//		FlowXml flowXml = new FlowXml();
//		flowXmls.add(flowXml);
//	}
	
	/**
	 * Test method for {
	 * @throws FlowException @link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#generate(FlowXml, cz.wicketstuff.boss.flow.builder.xml.FlowBuilderCarter)
	 */
	@Test
	public void testGenerateFlowBuilder() throws FlowException {
		FlowCodeGenerator generator = new FlowCodeGenerator();
		FlowBuilderCarter flowBuilderCarter = generator.buildFlow(getClass().getClassLoader().getResourceAsStream("test-full-flow.xml")); 
		FlowXml flowXml = new FlowXml();
		flowXml.setId("testId");
		flowXml.setPackageName("flow.generated");
		flowXml.setStateEnumName("StateEnum");
		flowXml.setTransitionEnumName("TransitionEnum");
		generator.generate(flowXml, flowBuilderCarter);
		File stateEnumFile = new File("flow/generated/StateEnum.java");
		assertTrue(stateEnumFile.exists());
		stateEnumFile.delete();
		File transitionEnumFile = new File("flow/generated/TransitionEnum.java");
		assertTrue(transitionEnumFile.exists());
		transitionEnumFile.delete();
	}
	
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
		List<EnumDescriptor<StateCapsule>> list = new ArrayList<>(10);
		FlowCodeGenerator generator = new FlowCodeGenerator();
		list.add(new EnumDescriptor<StateCapsule>("myName 1", null));
		list.add(new EnumDescriptor<StateCapsule>("xxName 1", null));
		list.add(new EnumDescriptor<StateCapsule>("MyName 1", null));
		list.add(new EnumDescriptor<StateCapsule>("MyNAME 1", null));
		list.add(new EnumDescriptor<StateCapsule>("test_01", null));
		list.add(new EnumDescriptor<StateCapsule>("MyNAME_1_01", null));
		list.add(new EnumDescriptor<StateCapsule>("test_01", null));
		generator.javaStyleNames(list);
		assertEquals("MY_NAME_1_01", list.get(0).getName());
		assertEquals("XX_NAME_1", list.get(1).getName());
		assertEquals("MY_NAME_1_02", list.get(2).getName());
		assertEquals("MY_NAME_1_03", list.get(3).getName());
		assertEquals("TEST_01", list.get(4).getName());
		assertEquals("MY_NAME_1_04", list.get(5).getName());
		assertEquals("TEST_02", list.get(6).getName());
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator#stripSuffix(String)}.
	 */
	@Test
	public void testStripSuffix() {
		FlowCodeGenerator generator = new FlowCodeGenerator();
		assertEquals("VALUE_1", generator.stripSuffix("VALUE_1"));
		assertEquals("VALUE", generator.stripSuffix("VALUE_01"));
		assertEquals("VALUE_101", generator.stripSuffix("VALUE_101"));
		assertEquals("1_VALUE_X", generator.stripSuffix("1_VALUE_X_10"));
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
