package cz.wicketstuff.boss.flow.builder.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.ext.DefaultFlowProcessor;
import cz.wicketstuff.boss.flow.test.AbstractFlowStepTest;
import cz.wicketstuff.boss.flow.test.FlowFileResource;

/**
 * @author Martin Strejc
 *
 */
public class DefaultFlowProcessorTest extends AbstractFlowStepTest {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	public DefaultFlowProcessorTest() {
		super();
		log.trace("Test complete flow builded by JAXB.");
	}

	@Override
	protected IFlowProcessor<String> createProcessor() throws FlowException {
		log.trace("Creating flow processor");
		DefaultFlowProcessor<String> processor = new DefaultFlowProcessor<String>();
		FlowFileResource resourceHelper = new FlowFileResource();
		processor.setFlowInputStream(resourceHelper.getCompleteFlowFileStream());
		processor.setDefaultInitialStateName(S0initialState);
		return processor.initializeProcessor();
	}

}
