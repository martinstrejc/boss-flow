package cz.wicketstuff.boss.flow.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cz.wicketstuff.boss.flow.builder.xml.DefaultFlowProcessorTest;
import cz.wicketstuff.boss.flow.builder.xml.FlowPersisterTest;
import cz.wicketstuff.boss.flow.builder.xml.JaxbFlowBuilderTest;

/**
 * @author Martin Strejc
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	DefaultFlowProcessorTest.class, 
	FlowPersisterTest.class,
	JaxbFlowBuilderTest.class })
public class BasicFlowTests {

}
