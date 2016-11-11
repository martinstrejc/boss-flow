package cz.wicketstuff.boss.flow.builder.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.wicketstuff.boss.flow.builder.xml.jaxb.ConditionStateType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.JoinStateType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.RealStateType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.StateType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.SwitchCaseType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.SwitchStateType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.TransitionIdentifierDefaultsType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.TransitionIdentifierType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.ViewStateType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.VirtualStateType;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.ISwitchCase;
import cz.wicketstuff.boss.flow.model.basic.FlowConditionState;
import cz.wicketstuff.boss.flow.model.basic.FlowJoinState;
import cz.wicketstuff.boss.flow.model.basic.FlowRealState;
import cz.wicketstuff.boss.flow.model.basic.FlowState;
import cz.wicketstuff.boss.flow.model.basic.FlowSwitchState;
import cz.wicketstuff.boss.flow.model.basic.FlowViewState;
import cz.wicketstuff.boss.flow.model.basic.FlowVirtualState;
import cz.wicketstuff.boss.flow.model.basic.SwitchCase;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;

/**
 * Class to build the complete graph with all references between
 * all transitions and all states.
 * 
 * see {@link #buildStateGraph(StateCapsule)}
 * 
 * @author Martin Strejc
 *
 */
public class StateGraphBuilder {
	
	/**
	 * Carter thats hold all data of flow
	 * necessary to build the graph.
	 */
	private FlowBuilderCarter carter;

	/**
	 * Default constructor. It the default constructor is used,
	 * do not forget call {@link #setCarter(FlowBuilderCarter)}
	 * before {@link #buildStateGraph(StateCapsule)}
	 */
	public StateGraphBuilder() {
	}

	/**
	 * Constructor with data to build.
	 * 
	 * @param carter flow descriptor holder
	 */
	public StateGraphBuilder(FlowBuilderCarter carter) {
		this.carter = carter;
	}

	/**
	 * Return the data holder/carter that is used to build.
	 * 
	 * @return data holder/carter
	 */
	public FlowBuilderCarter getCarter() {
		return carter;
	}

	/**
	 * Set the data holder/carter that is used to build flow graph.
	 * 
	 * @param carter data holder
	 */
	public void setCarter(FlowBuilderCarter carter) {
		this.carter = carter;
	}
	
	/**
	 * Build whole graph
	 * 
	 * @param stateCapsule
	 * @throws NoSuchTransitionException
	 * @throws InvalidFlowAttributeException 
	 */
	public void buildStateGraph(StateCapsule stateCapsule) throws NoSuchTransitionException, InvalidFlowAttributeException {
		StateType state = stateCapsule.getState();
		FlowState flowState = stateCapsule.getFlowState();
		if(state instanceof ViewStateType) {
			buildFlowViewState((FlowViewState) flowState, (ViewStateType)state);
		} else if(state instanceof RealStateType) {
			buildFlowRealState((FlowRealState) flowState, (RealStateType)state);
		} else if(state instanceof JoinStateType) {
			buildFlowJoinState((FlowJoinState) flowState, (JoinStateType)state);
		} else if(state instanceof ConditionStateType) {
			buildFlowConditionState((FlowConditionState) flowState, (ConditionStateType)state);
		} else if(state instanceof SwitchStateType) {
			buildFlowSwitchState((FlowSwitchState) flowState, (SwitchStateType)state);
		} else if(state instanceof VirtualStateType) {
			buildFlowVirtualState((FlowVirtualState) flowState, (VirtualStateType)state);
		} else {
			buildDefaultFlowState(flowState, state);
		}		
	}
	
	public TransitionCapsule findTransition(TransitionIdentifierType identifier) throws NoSuchTransitionException {
		try {
			TransitionCapsule tc = getCarter().findTransition(identifier.getRefName());
			if(tc == null) {
				throw new NoSuchTransitionException("Cannot find flowTransition with name=" + identifier.getRefName());
			}
			return tc;
		} catch (IllegalArgumentException e) {
			throw new NoSuchTransitionException("Incorrect target flowTransition definition: id=null and name=null", e);				
		}
	}
	
	protected void buildOutTransitions(FlowState flowState, StateType state) throws NoSuchTransitionException {
		Map<String, IFlowTransition> transitionsMap = new HashMap<String, IFlowTransition>();
		for(TransitionIdentifierDefaultsType ti : state.getTransition()) {
			IFlowTransition t = findTransition(ti).getFlowTransition();
			transitionsMap.put(t.getTransitionName(), t);
		}
		flowState.setTransitionsMap(transitionsMap);
	}

	protected void buildDefaultPreviousNextTransitions(FlowRealState realState, RealStateType realStateType) throws NoSuchTransitionException, InvalidFlowAttributeException {
		for(TransitionIdentifierDefaultsType ti : realStateType.getTransition()) {
			boolean defaultNext = ti.isDefaultNext();
			boolean defaultPrevious = ti.isDefaultPrevious();
			if(defaultNext || defaultPrevious) {
				IFlowTransition t = findTransition(ti).getFlowTransition(); 
				if(defaultNext && defaultPrevious) {
					throw new InvalidFlowAttributeException("Flow transition cannot has both of next and previous attribute, check state '" + realState.getStateName() + "' and transition '" + t.getTransitionName() + "'.");
				}
				if(defaultNext) {
					if(realState.getDefaultNextTransition() != null) {
						throw new InvalidFlowAttributeException("Default next transition has been already defined, check state '" + realState.getStateName() + "' and transition '" + t.getTransitionName() + "'.");						
					}
					realState.setDefaultNextTransition(t);
				}
				if(defaultPrevious) {
					if(realState.getDefaultPreviousTransition() != null) {
						throw new InvalidFlowAttributeException("Default previous transition has been already defined, check state '" + realState.getStateName() + "' and transition '" + t.getTransitionName() + "'.");						
					}
					realState.setDefaultPreviousTransition(t);
				}
			}
		}
	}

	protected void buildFlowViewState(FlowViewState flowState, ViewStateType state) throws NoSuchTransitionException, InvalidFlowAttributeException {
		buildOutTransitions(flowState, state);
		buildDefaultPreviousNextTransitions(flowState, state);
	}
	
	protected void buildFlowRealState(FlowRealState flowState, RealStateType state) throws NoSuchTransitionException, InvalidFlowAttributeException {
		buildOutTransitions(flowState, state);
		buildDefaultPreviousNextTransitions(flowState, state);
	}
	
	protected void buildFlowJoinState(FlowJoinState flowState, JoinStateType state) throws NoSuchTransitionException {
		flowState.setNextTransition(findTransition(state.getNextTransition()).getFlowTransition());
		flowState.rebuildTransitionMap();		
	}
	
	protected void buildFlowConditionState(FlowConditionState flowState, ConditionStateType state) throws NoSuchTransitionException {
		if(state.getThenTransition() == null & state.getElseTransition() == null) {
			throw new NoSuchTransitionException("Cannot build if condition of " + state.getId() + ":" + state.getName() + " for nulls in both of then/else transitions.");
		}
		
		if(state.getThenTransition() == null) { 
			flowState.setThenTransition(null);
		} else {
			flowState.setThenTransition(findTransition(state.getThenTransition()).getFlowTransition());			
		}
		
		if(state.getElseTransition() == null) { 
			flowState.setElseTransition(null);
		} else {
			flowState.setElseTransition(findTransition(state.getElseTransition()).getFlowTransition());
		}
		flowState.rebuildTransitionMap();
	}
	
	protected void buildFlowSwitchState(FlowSwitchState flowState, SwitchStateType state) throws NoSuchTransitionException {
		flowState.setDefaultTransition(findTransition(state.getDefaultTransition()).getFlowTransition());
		flowState.setDefaultTransition(findTransition(state.getDefaultTransition()).getFlowTransition());
		List<ISwitchCase> scList = new ArrayList<ISwitchCase>();
		for(SwitchCaseType sct : state.getSwitchCase()) {
			scList.add(new SwitchCase(sct.getCaseValue(), findTransition(sct.getTransition()).getFlowTransition()));
			
		}
		flowState.setSwitchCasesList(scList);
		flowState.rebuildTransitionMap();		
	}
	
	protected void buildFlowVirtualState(FlowVirtualState flowState, VirtualStateType state) throws NoSuchTransitionException {
		buildOutTransitions(flowState, state);		
	}
	
	protected void buildDefaultFlowState(FlowState flowState, StateType state) throws NoSuchTransitionException {
		buildOutTransitions(flowState, state);		
	}

	@Override
	protected void finalize() throws Throwable {
		carter = null;
		super.finalize();
	}

}