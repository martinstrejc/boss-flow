package cz.wicketstuff.boss.flow.processor.ext;

import java.io.InputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.builder.IFlowBuilder;
import cz.wicketstuff.boss.flow.builder.xml.JaxbFlowBuilder;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.model.basic.FlowCarter;
import cz.wicketstuff.boss.flow.processor.IFlowCarterFactory;
import cz.wicketstuff.boss.flow.processor.IFlowProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStatePersister;
import cz.wicketstuff.boss.flow.processor.IFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.IFlowStateResolver;
import cz.wicketstuff.boss.flow.processor.IFlowTransitionResolver;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;
import cz.wicketstuff.boss.flow.processor.basic.DefaultFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.basic.SimpleFlowProcessor;
import cz.wicketstuff.boss.flow.processor.basic.SimpleFlowStateProcessor;
import cz.wicketstuff.boss.flow.processor.basic.SimpleFlowStateResolver;
import cz.wicketstuff.boss.flow.processor.basic.SimpleFlowTransitionResolver;

/**
 * @author Martin Strejc
 *
 * @param <T>
 */
public class DefaultFlowProcessor<T extends Serializable> extends
		SimpleFlowProcessor<T> {

	private static final Logger log = LoggerFactory.getLogger(DefaultFlowProcessor.class);
	
	private static final long serialVersionUID = 1L;

	private String defaultInitialStateName;

	private IFlowTree flowTree;
	
	private InputStream flowInputStream;

	/**
	 * Default constructor
	 */
	public DefaultFlowProcessor() {
		super();
	}

	public IFlowProcessor<T> initializeProcessor() throws FlowException {
		onInitializeProcessor();
		IFlowBuilder flowBuilder = defaultFlowBuilder();
		flowTree = flowBuilder.buildFlowTree(
				getFlowInputStream(), getFlowId(), getFlowName());
		setCarterFactory(defaultCarterFactory());
		setStateProcessor(defaultFlowStateProcessor(flowTree));
		setStateResolver(defaultFlowStateResolver(flowTree));
		setTransitionResolver(defaultFlowTransitionResolver(flowTree));
		scanAnnotedBeans();
		IFlowState initialState = defaultInitialState();
		if(initialState != null) {
			setDefaultInitialState(initialState);			
		}
		if(getDefaultInitialState() == null) {
			log.warn("Default flow initial state is NULL!");
		}
		IFlowStatePersister<T> statePersister = defaultFlowStatePersister();
		if(statePersister != null) {
			setFlowStatePersister(statePersister);			
		}
		if(getFlowStatePersister() == null) {
			log.warn("Missing flow state persister");
		}
		onAfterInitializeProcessor();
		return this;
	}

	public void onInitializeProcessor() {

	}

	public void onAfterInitializeProcessor() {

	}

	public IFlowCarter<T> defaultFlowCarter(Long flowProcessId,
			IFlowState initialState) {
		return new FlowCarter<T>(flowProcessId, initialState);
	}

	public IFlowCarterFactory<T> defaultCarterFactory() {
		return new IFlowCarterFactory<T>() {

			@Override
			public IFlowCarter<T> createFlowCarter(Long flowProcessId,
					IFlowState initialState) throws FlowException {
				return defaultFlowCarter(flowProcessId, initialState);
			}
		};
	}

	public void scanAnnotedBeans() throws FlowAnnotationException {
		scanAnnotedBeans(defaultConditionBean(), defaultSwitchBean(),
				defaultStateListenerBean(), defaultTransitionListenerBean());
	}

	public void scanAnnotedBeans(Object conditionBean, Object switchBean,
			Object stateListenerBean, Object transitionListenerBean)
			throws FlowAnnotationException {
		SimpleFlowStateProcessor<T> stateProcessor = getSimpleFlowStateProcessor();
		AnnotationFlowFactory<T> annotFactory = defaultAnnotationFlowFactory(getFlowTree());
		if (conditionBean != null) {
			stateProcessor.setConditionProcessor(annotFactory
					.getFlowConditionProcessors(conditionBean));
		}
		if (switchBean != null) {
			stateProcessor.setSwitchProcessor(annotFactory
					.getFlowSwitchProcessors(switchBean));
		}
		if (stateListenerBean != null) {
			setStateChangeListener(annotFactory
					.getStateChangeListeners(stateListenerBean));
		}
		if (transitionListenerBean != null) {
			setTransitionChangeListener(annotFactory
					.getTransitionChangeListeners(transitionListenerBean));
		}
	}

	public Object defaultConditionBean() {
		return null;
	}

	public Object defaultSwitchBean() {
		return null;
	}

	public Object defaultStateListenerBean() {
		return null;
	}

	public Object defaultTransitionListenerBean() {
		return null;
	}

	public IFlowStatePersister<T> defaultFlowStatePersister() {
		return null;
	}
	
	protected SimpleFlowStateProcessor<T> getSimpleFlowStateProcessor() {
		IFlowStateProcessor<T> p = getStateProcessor();
		if (p instanceof SimpleFlowStateProcessor) {
			return (SimpleFlowStateProcessor<T>) p;
		}
		throw new ClassCastException(
				"An instance of SimpleFlowStateProcessor<T> is required. Do not use annotation factory that need this class or check your class hierarchy.");
	}

	public IFlowBuilder defaultFlowBuilder() {
		return JaxbFlowBuilder.newInstance();
	}

	public IFlowStateProcessor<T> defaultFlowStateProcessor(IFlowTree flowTree) {
		return new DefaultFlowStateProcessor<T>();
	}

	public IFlowStateResolver defaultFlowStateResolver(IFlowTree flowTree) {
		return new SimpleFlowStateResolver(flowTree);
	}

	public IFlowTransitionResolver<T> defaultFlowTransitionResolver(
			IFlowTree flowTree) {
		return new SimpleFlowTransitionResolver<T>(flowTree);
	}

	public AnnotationFlowFactory<T> defaultAnnotationFlowFactory(
			IFlowTree flowTree) {
		return new AnnotationFlowFactory<T>();
	}

	public IFlowState defaultInitialState() throws NoSuchStateException {
		String stateName = getDefaultInitialStateName();
		if(stateName == null) {
			return flowTree.getDefaultInitialState();
		}
		return getStateResolver().resolveState(stateName);
	}

	public String getDefaultInitialStateName() {
		return defaultInitialStateName;
	}

	public void setDefaultInitialStateName(String defaultInitialStateName) {
		this.defaultInitialStateName = defaultInitialStateName;
	}

	public IFlowTree getFlowTree() {
		return flowTree;
	}

	public void setFlowTree(IFlowTree flowTree) {
		this.flowTree = flowTree;
	}

	public InputStream getFlowInputStream() {
		return flowInputStream;
	}

	public void setFlowInputStream(InputStream flowInputStream) {
		this.flowInputStream = flowInputStream;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		defaultInitialStateName = null;
		flowTree = null;
		flowInputStream = null;
	}
	
	

}
