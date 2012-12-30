package cz.wicketstuff.boss.flow.processor.ext;

import java.io.InputStream;
import java.io.Serializable;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.builder.xml.JaxbFlowBuilder;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.model.basic.FlowCarter;
import cz.wicketstuff.boss.flow.processor.IFlowCarterFactory;
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

	private static final long serialVersionUID = 1L;

	private String defaultInitialStateName;

	private IFlowTree flowTree;
	
	private InputStream flowXmlStream;

	/**
	 * Default constructor
	 */
	public DefaultFlowProcessor() {
		super();
	}

	public void initializeFlow() throws FlowException {
		onInitializeFlow();
		JaxbFlowBuilder flowBuilder = defaultFlowBuilder();
		IFlowTree flowTree = flowBuilder.buildFlowTree(
				getFlowXmlStream(), getFlowId(), getFlowName());
		setCarterFactory(defaultCarterFactory());
		setStateProcessor(defaultFlowStateProcessor(flowTree));
		setStateResolver(defaultFlowStateResolver(flowTree));
		setTransitionResolver(defaultFlowTransitionResolver(flowTree));
		scanAnnotedBeans();
		setDefaultInitialState(defaultInitialState());
		onAfterInitializeFlow();
	}

	public void onInitializeFlow() {

	}

	public void onAfterInitializeFlow() {

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

	protected SimpleFlowStateProcessor<T> getSimpleFlowStateProcessor() {
		IFlowStateProcessor<T> p = getStateProcessor();
		if (p instanceof SimpleFlowProcessor) {
			return (SimpleFlowStateProcessor<T>) p;
		}
		throw new ClassCastException(
				"An instance of SimpleFlowStateProcessor<T> is required. Do not use annotation factory that need this class or check your class hierarchy.");
	}

	public JaxbFlowBuilder defaultFlowBuilder() {
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
		return getStateResolver().resolveState(getDefaultInitialStateName());
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

	public InputStream getFlowXmlStream() {
		return flowXmlStream;
	}

	public void setFlowXmlStream(InputStream flowXmlStream) {
		this.flowXmlStream = flowXmlStream;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		defaultInitialStateName = null;
		flowTree = null;
		flowXmlStream = null;
	}
	
	

}
