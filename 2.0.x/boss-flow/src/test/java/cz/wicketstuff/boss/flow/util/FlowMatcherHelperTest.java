/**
 * 
 */
package cz.wicketstuff.boss.flow.util;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.wicketstuff.boss.flow.model.IFlowCategorized;
import cz.wicketstuff.boss.flow.model.basic.FlowCategory;
import cz.wicketstuff.boss.flow.model.basic.FlowState;

/**
 * @author Martin Strejc
 *
 */
public class FlowMatcherHelperTest {

	private static IFlowCategorized flowCategorized1;
	private static IFlowCategorized flowCategorized2;
	private static Pattern patternMatch1 = Pattern.compile("Cat.*");
	private static Pattern patternMatch2 = Pattern.compile("pat.*");
	private static Pattern patternNotMatch = Pattern.compile("X.*");
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FlowState flowState = new FlowState("testState1");
		flowState.addFlowCategory(new FlowCategory("Cat1"));
		flowState.addFlowCategory(new FlowCategory("Cat2"));
		flowState.addFlowCategory(new FlowCategory("pattern"));
		flowCategorized1 = flowState;
		flowCategorized2 = new FlowState("testState2");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		flowCategorized1 = null;
		flowCategorized2 = null;
		patternMatch1 = null;
		patternMatch2 = null;
		patternNotMatch = null;
	}
	
	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.util.FlowMatcherHelper#matchesAny(cz.wicketstuff.boss.flow.model.IFlowCategorized, java.lang.String)}.
	 */
	@Test
	public void testMatchesAnyIFlowCategorizedString() {
		assertTrue(FlowMatcherHelper.matchesAny(flowCategorized1, patternMatch1));
		assertTrue(FlowMatcherHelper.matchesAny(flowCategorized1, patternMatch2));
		assertTrue(FlowMatcherHelper.matchesAny(flowCategorized1, (Pattern)null));
		assertFalse(FlowMatcherHelper.matchesAny(flowCategorized1, patternNotMatch));
		assertFalse(FlowMatcherHelper.matchesAny(flowCategorized2, patternMatch1));
		assertFalse(FlowMatcherHelper.matchesAny(flowCategorized2, patternNotMatch));
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.util.FlowMatcherHelper#matchesAny(cz.wicketstuff.boss.flow.model.IFlowCategorized, java.util.regex.Pattern)}.
	 */
	@Test
	public void testMatchesAnyIFlowCategorizedPattern() {
		assertTrue(FlowMatcherHelper.matchesAny(flowCategorized1, patternMatch1.toString()));
		assertTrue(FlowMatcherHelper.matchesAny(flowCategorized1, patternMatch2.toString()));
		assertTrue(FlowMatcherHelper.matchesAny(flowCategorized1, (String)null));
		assertFalse(FlowMatcherHelper.matchesAny(flowCategorized1, patternNotMatch.toString()));
		assertFalse(FlowMatcherHelper.matchesAny(flowCategorized2, patternMatch1.toString()));
		assertFalse(FlowMatcherHelper.matchesAny(flowCategorized2, patternNotMatch.toString()));
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.util.FlowMatcherHelper#matchesNone(cz.wicketstuff.boss.flow.model.IFlowCategorized, java.lang.String)}.
	 */
	@Test
	public void testMatchesNoneIFlowCategorizedString() {
		assertTrue(FlowMatcherHelper.matchesNone(flowCategorized1, patternNotMatch));
		assertFalse(FlowMatcherHelper.matchesNone(flowCategorized1, patternMatch1));
		assertFalse(FlowMatcherHelper.matchesNone(flowCategorized1, patternMatch2));
		assertFalse(FlowMatcherHelper.matchesNone(flowCategorized1, (Pattern)null));
		assertTrue(FlowMatcherHelper.matchesNone(flowCategorized2, patternMatch1));
		assertTrue(FlowMatcherHelper.matchesNone(flowCategorized2, patternNotMatch));
	}

	/**
	 * Test method for {@link cz.wicketstuff.boss.flow.util.FlowMatcherHelper#matchesNone(cz.wicketstuff.boss.flow.model.IFlowCategorized, java.util.regex.Pattern)}.
	 */
	@Test
	public void testMatchesNoneIFlowCategorizedPattern() {
		assertTrue(FlowMatcherHelper.matchesNone(flowCategorized1, patternNotMatch.toString()));
		assertFalse(FlowMatcherHelper.matchesNone(flowCategorized1, patternMatch1.toString()));
		assertFalse(FlowMatcherHelper.matchesNone(flowCategorized1, patternMatch2.toString()));
		assertFalse(FlowMatcherHelper.matchesNone(flowCategorized1, (String)null));
		assertTrue(FlowMatcherHelper.matchesNone(flowCategorized2, patternMatch1.toString()));
		assertTrue(FlowMatcherHelper.matchesNone(flowCategorized2, patternNotMatch.toString()));
	}

	
}
