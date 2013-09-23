/**
 * 
 */
package cz.wicketstuff.boss.flow.processor.ext;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.processor.FlowListenerException;

/**
 * Default dummy implementation that do nothing
 * 
 * @author Martin Strejc
 *
 */
public class DefaultFilteredFlowPersisterListener<T extends Serializable> extends
		FilteredFlowPersisterListener<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public DefaultFilteredFlowPersisterListener() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param priority
	 */
	public DefaultFilteredFlowPersisterListener(int priority) {
		super(priority);
	}

	@Override
	protected void onFlowBeforePersistedFiltered(IFlowCarter<T> flow)
			throws FlowListenerException {
	}

	@Override
	protected void onFlowPersistedFiltered(IFlowCarter<T> flow)
			throws FlowListenerException {
	}

	@Override
	protected void onFlowRestoredFiltered(IFlowCarter<T> flow)
			throws FlowListenerException {
	}

}
