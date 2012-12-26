package com.etnetera.boss.flow.processor;

import java.io.Serializable;

import com.etnetera.boss.flow.FlowException;
import com.etnetera.boss.flow.model.IFlowCarter;

public interface IFlowStateProcessor<T extends Serializable> {

	void processState(IFlowCarter<T> flow) throws FlowException;
	
}
