package cz.wicketstuff.boss.flow.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cz.wicketstuff.boss.flow.processor.basic.DefaultFlowStateOrdinalComparatorTest;

/**
 * @author Martin Strejc
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	DefaultFlowStateOrdinalComparatorTest.class
	})
public class BasicJUnitTests {

}
