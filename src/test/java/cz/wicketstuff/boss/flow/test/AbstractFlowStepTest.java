package cz.wicketstuff.boss.flow.test;

import static org.junit.Assert.*;

import org.junit.Test;


import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.builder.xml.FlowBuilderCarter;

public abstract class AbstractFlowStepTest {

	public AbstractFlowStepTest() {
		super();
	}
	
	abstract protected FlowBuilderCarter buildFlow() throws FlowException;

	@Test
	public void testIfTrueToFinal() throws FlowException {
		fail("Test not defined yet.");
	}

	@Test
	public void testSwitchDefaultIfFalse() throws FlowException {
		fail("Test not defined yet.");
	}

	@Test
	public void testSwitchToViewS6() throws FlowException {
		fail("Test not defined yet.");
	}

	@Test
	public void testSwitchToFinal() throws FlowException {
		fail("Test not defined yet.");
	}

	@Test
	public void testAfterFinal() throws FlowException {
		fail("Test not defined yet.");
	}

	@Test
	public void testSecondaryInitial() throws FlowException {
		fail("Test not defined yet.");
	}

	@Test
	public void testWrongInitial() throws FlowException {
		fail("Test not defined yet.");
	}

}
