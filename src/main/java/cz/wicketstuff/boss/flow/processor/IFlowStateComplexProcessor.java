package cz.wicketstuff.boss.flow.processor;

import java.io.Serializable;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowCarter;
import cz.wicketstuff.boss.flow.model.IFlowConditionState;
import cz.wicketstuff.boss.flow.model.IFlowJoinState;
import cz.wicketstuff.boss.flow.model.IFlowRealState;
import cz.wicketstuff.boss.flow.model.IFlowSwitchState;
import cz.wicketstuff.boss.flow.model.IFlowViewState;
import cz.wicketstuff.boss.flow.model.IFlowVirtualState;

public interface IFlowStateComplexProcessor<T extends Serializable>  extends IFlowStateProcessor<T> {
	
	void processRealState(IFlowCarter<T> flow, IFlowRealState currentState) throws FlowException;

	void processVirtualState(IFlowCarter<T> flow, IFlowVirtualState currentState) throws FlowException;

	void processViewState(IFlowCarter<T> flow, IFlowViewState currentState) throws FlowException;

	void processConditionState(IFlowCarter<T> flow, IFlowConditionState currentState) throws FlowException;

	void processSwitchState(IFlowCarter<T> flow, IFlowSwitchState currentState) throws FlowException;

	void processJoinState(IFlowCarter<T> flow, IFlowJoinState currentState) throws FlowException;

	void processUknownState(IFlowCarter<T> flow) throws FlowException;

}
