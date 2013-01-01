package cz.wicketstuff.boss.flow.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;

public abstract class AbstractFlowStepTest implements ICompleteFlowTest {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	public final String payload = "test payload";
	public final long flowId = 17L;
	
	private IFlowProcessor<String> processor;
	
	private IFlowCarter<String> carter;
	
	@Before
	public void setUp() throws FlowException {
		processor = createProcessor();
		carter = null;		
	}

	@After
	public void tearDown() {
		processor = null;
		carter = null;		
	}
	
	public AbstractFlowStepTest() {
		super();
	}
	
	
	abstract protected IFlowProcessor<String> createProcessor() throws FlowException;

	protected void initializeCarter() throws FlowException {
		carter = null;
		carter = processor.initFlow(flowId, payload, S0initialState);
	}
	
	@Test
	public void testInitialization() throws FlowException {
		log.trace("Test flow initilization.");
		initializeCarter();
		assertNotNull("Carter initialization cannot be null", carter);
		assertEquals("FlowId must be the same value whole the time", (long)flowId, (long)carter.getFlowProcessId());
		assertEquals("Payload must be store in the flow carter.", payload, carter.getPayload());		
		log.trace("Test initial state.");
		checkCurrentState(S0initialState);
	}

	@Test
	public void testIfTrueToFinal() throws FlowException {
		// fail("Test not defined yet.");
	}

	@Test
	public void testSwitchDefaultIfFalse() throws FlowException {
		// fail("Test not defined yet.");
	}

	@Test
	public void testSwitchToViewS6() throws FlowException {
		// fail("Test not defined yet.");
	}

	@Test
	public void testSwitchToFinal() throws FlowException {
		// fail("Test not defined yet.");
	}

	@Test
	public void testAfterFinal() throws FlowException {
		// fail("Test not defined yet.");
	}

	@Test
	public void testSecondaryInitial() throws FlowException {
		// fail("Test not defined yet.");
	}

	@Test
	public void testWrongInitial() throws FlowException {
		// fail("Test not defined yet.");
	}


	public IFlowProcessor<String> getProcessor() {
		return processor;
	}

	public void setProcessor(IFlowProcessor<String> processor) {
		this.processor = processor;
	}


	public IFlowCarter<String> getCarter() {
		return carter;
	}

	public void setCarter(IFlowCarter<String> carter) {
		this.carter = carter;
	}
	
	public IFlowState getCurrentState() {
		return getCarter().getCurrentState();
	}
	
	protected void checkCurrentState(String stateName) {
		assertEquals("Wrong current state of the flow", stateName, getCurrentState().getStateName());
	}
	
}
