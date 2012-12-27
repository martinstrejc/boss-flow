package cz.wicketstuff.boss.flow.processor;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;

public interface IFlowStateProcessor<T extends Serializable> {

	void processState(IFlowCarter<T> flow) throws FlowException;
	
}
