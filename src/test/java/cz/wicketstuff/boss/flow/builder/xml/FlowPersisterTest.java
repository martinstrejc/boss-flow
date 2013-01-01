package cz.wicketstuff.boss.flow.builder.xml;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.processor.FlowPersistingException;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStatePersister;
import cz.wicketstuff.boss.flow.processor.IFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.ext.DefaultFlowProcessor;
import cz.wicketstuff.boss.flow.test.AbstractFlowTest;
import cz.wicketstuff.boss.flow.test.FlowFileResource;
import cz.wicketstuff.boss.flow.util.listener.IPriority;

/**
 * @author Martin Strejc
 *
 */
public class FlowPersisterTest extends AbstractFlowTest implements IFlowStatePersister<String> {

	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	private List<IFlowState> persistedList = new ArrayList<IFlowState>();
	private List<String> persistedNameList = new ArrayList<String>();

	public FlowPersisterTest() {
		super();
		log.trace("Test complete flow builded by JAXB.");
	}

	@Override
	protected IFlowProcessor<String> createProcessor() throws FlowException {
		log.trace("Creating flow processor");
		DefaultFlowProcessor<String> processor = new DefaultFlowProcessor<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public IFlowStateProcessor<String> defaultFlowStateProcessor(IFlowTree flowTree) {
				return createFlowStateProcessor();
			}

			@Override
			public IFlowStatePersister<String> defaultFlowStatePersister() {
				return FlowPersisterTest.this;
			}

		};
		FlowFileResource resourceHelper = new FlowFileResource();
		processor.setFlowInputStream(resourceHelper.getCompleteFlowFileStream());
		// processor.setDefaultInitialStateName(S0initialState);
		// not applicable here
		// processor.scanAnnotedBeans(this, this, this, this);
		return processor.initializeProcessor();
	}

	public List<IFlowState> getPersistedList() {
		return persistedList;
	}

	public List<String> getPersistedNameList() {
		return persistedNameList;
	}


	@Override
	public int compareTo(IPriority o) {
		return 0;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void persistFlowState(IFlowCarter<String> flow)
			throws FlowPersistingException {
		log.trace("Persisting: " + flow.getCurrentState().getStateName());
		getPersistedList().add(flow.getCurrentState());
		getPersistedNameList().add(flow.getCurrentState().getStateName());
		
	}

	@Test
	public void testSwitchDefaultIfFalsePersister() throws FlowException {
		List<String> stateThrough = new ArrayList<String>();
		persistedList.clear();
		stateThrough.clear();
		log.trace("Test persister");
		initializeCarter();
		stateThrough.add(S0initialState);
		processor.invokeTransition(carter, T01);
		stateThrough.add(S1realState);
		setIfExpressionResult(false);
		setSwitchExpressionResult(null);
		processor.invokeTransition(carter, T15);
		stateThrough.add(S5switchState);
		stateThrough.add(S2ifState);
		stateThrough.add(S4viewState);
		setSwitchExpressionResult(CASE_toViewS6);
		processor.invokeTransition(carter, T45);
		stateThrough.add(S5switchState);
		stateThrough.add(S6viewStateInitial);
		processor.invokeTransition(carter, T68);
		// not persistable
		// stateThrough.add(S8joinState);
		stateThrough.add(S9finalState);
		assertArrayEquals("Persisted list doesn't match", stateThrough.toArray(), persistedNameList.toArray());
	}

	
}
