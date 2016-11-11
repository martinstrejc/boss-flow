/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.wicketstuff.boss.flow.builder.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.builder.IFlowBuilder;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.FlowDescriptorType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.StateType;
import cz.wicketstuff.boss.flow.builder.xml.jaxb.TransitionType;
import cz.wicketstuff.boss.flow.model.IFlowState;
import cz.wicketstuff.boss.flow.model.IFlowTransition;
import cz.wicketstuff.boss.flow.model.IFlowTree;
import cz.wicketstuff.boss.flow.model.basic.FlowTree;
import cz.wicketstuff.boss.flow.processor.NoSuchStateException;
import cz.wicketstuff.boss.flow.processor.NoSuchTransitionException;

/**
 * The main class to build flow from an XML.
 * See {@link #buildFlowTree(FlowBuilderCarter, Integer, String)}
 * 
 * @author Martin Strejc
 *
 */
public class JaxbFlowBuilder implements IFlowBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(JaxbFlowBuilder.class);
	
	private FlowObjectFactory flowObjectFactory;

	protected JaxbFlowBuilder() {
	}
	
	public static JaxbFlowBuilder newInstance() {
		return newInstance(new FlowObjectFactory());
	} 

	public static JaxbFlowBuilder newInstance(FlowObjectFactory flowObjectFactory) {
		JaxbFlowBuilder builder = new JaxbFlowBuilder();
		builder.setFlowObjectFactory(flowObjectFactory);
		return builder;
	} 

	protected TransitionCapsule newTransitionCapsule(TransitionType transition) {
		return new TransitionCapsule(transition, getFlowObjectFactory().createFlowTransition(transition));
	}

	protected StateCapsule newStateCapsule(StateType state) {
		return new StateCapsule(state, getFlowObjectFactory().createFlowState(state));
	}

	protected void fillTransitionMaps(List<? extends TransitionType> transitions, Map<String, TransitionCapsule> transitionNamesMap, Map<Integer, TransitionCapsule> transitionIdsMap) throws DuplicatedTransitionException {
		for(TransitionType t : transitions) {
			fillTransitionMaps(t, transitionNamesMap, transitionIdsMap);
		}		
	}

	protected void fillTransitionMaps(TransitionType transition, Map<String, TransitionCapsule> transitionNamesMap, Map<Integer, TransitionCapsule> transitionIdsMap) throws DuplicatedTransitionException {
		TransitionCapsule tc = newTransitionCapsule(transition);
		if(logger.isDebugEnabled()) {
			logger.debug("fill transition " + transition.getId() + ": " + transition.getName() + " -> " + transition.getTargetStateName());											
		}
		if(transitionIdsMap.containsKey(transition.getId())) {
			throw new DuplicatedTransitionException("Duplicated ID, cannot place transition id=" + transition.getId() + ", name=" + transition.getName() + " has been already found by id.");
		}
		if(transitionNamesMap.containsKey(transition.getName())) {
			throw new DuplicatedTransitionException("Duplicated NAME, cannot place transition id=" + transition.getId() + ", name=" + transition.getName() + " has been already found by name.");
		}
		transitionIdsMap.put(transition.getId(), tc);
		transitionNamesMap.put(transition.getName(), tc);
	}

	protected void fillStateMaps(List<? extends StateType> states, Map<String, StateCapsule> stateNamesMap, Map<Integer, StateCapsule> stateIdsMap) throws DuplicatedStateException {
		for(StateType s : states) {
			fillStateMaps(s, stateNamesMap, stateIdsMap);
		}		
	}
	
	protected void fillStateMaps(StateType state, Map<String, StateCapsule> stateNamesMap, Map<Integer, StateCapsule> stateIdsMap) throws DuplicatedStateException {
		StateCapsule sc = newStateCapsule(state);
		if(logger.isDebugEnabled()) {
			logger.debug("fill state " + state.getId() + ": " + state.getName() + " of '" + state.getClass().getSimpleName() + "' ");											
		}
		if(stateIdsMap.containsKey(state.getId())) {
			throw new DuplicatedStateException("Duplicated ID, cannot place state id=" + state.getId() + ", name=" + state.getName() + " has been already found by id.");
		}
		if(stateNamesMap.containsKey(state.getName())) {
			throw new DuplicatedStateException("Duplicated NAME, cannot place state id=" + state.getId() + ", name=" + state.getName() + " has been already found by name.");
		}
		stateIdsMap.put(state.getId(), sc);
		stateNamesMap.put(state.getName(), sc);
	}
	
	protected void buildTransitionGraph(FlowBuilderCarter carter) throws NoSuchStateException {
		if(logger.isDebugEnabled()) {
			logger.debug("Building transition graph");											
		}
		for(Iterator<TransitionCapsule> ti = carter.getTransitionIdsMap().values().iterator(); ti.hasNext() ; ) {
			TransitionCapsule tc = ti.next();			
			try {
				StateCapsule sc = carter.findState(tc.getTransition().getTargetStateName());
				if(sc == null) {
					throw new NoSuchStateException("Cannot find flowState with name=" + tc.getTransition().getTargetStateName());
				}
				tc.getFlowTransition().setTargetState(sc.getFlowState());
			} catch (IllegalArgumentException e) {
				throw new NoSuchStateException("Incorrect target flowState definition: id=null and name=null", e);				
			}
		}
	}

	protected void buildStateGraph(FlowBuilderCarter carter) throws NoSuchTransitionException, InvalidFlowAttributeException {
		if(logger.isDebugEnabled()) {
			logger.debug("Building states graph");											
		}
		StateGraphBuilder stateGraphBuilder = newStateGraphBuilder(carter);
		for(Iterator<StateCapsule> si = carter.getStateIdsMap().values().iterator(); si.hasNext() ; ) {
			StateCapsule sc = si.next();
			stateGraphBuilder.buildStateGraph(sc);
		}
	}
	
	protected StateGraphBuilder newStateGraphBuilder(FlowBuilderCarter carter) {
		return new StateGraphBuilder(carter);
	} 

	public FlowBuilderCarter buildFlow(FlowDescriptorType flowDescriptor) throws FlowException {
		if(logger.isDebugEnabled()) {
			logger.debug("Building flow " + flowDescriptor.getFlowId() + ": " + flowDescriptor.getFlowName() + " (" + flowDescriptor.getFlowDescription() + ")");											
		}
		Map<Integer, StateCapsule> stateIdsMap = new HashMap<Integer, StateCapsule>();
		Map<String, StateCapsule> stateNamesMap = new HashMap<String, StateCapsule>();
		Map<Integer, TransitionCapsule> transitionIdsMap = new HashMap<Integer, TransitionCapsule>();
		Map<String, TransitionCapsule> transitionNamesMap = new HashMap<String, TransitionCapsule>();
		if(logger.isDebugEnabled()) {
			logger.debug("Processing transition map");											
		}
		IFlowState defaultInitialState = null;
		for(Object o : flowDescriptor.getStateOrRealStateOrViewState()) {
			if(o instanceof TransitionType) {
				fillTransitionMaps((TransitionType) o, transitionNamesMap, transitionIdsMap);
			}
			if(o instanceof StateType) {
				StateType st = (StateType) o;
				fillStateMaps(st, stateNamesMap, stateIdsMap);
				if(defaultInitialState == null && st.isDefaultInitialState()) {
					defaultInitialState = stateNamesMap.get(st.getName()).getFlowState();
					if(logger.isTraceEnabled()) {
						logger.trace("Found default initial state: " + defaultInitialState.getStateName());
					}
				}
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("Creating carter");											
		}
		FlowBuilderCarter carter = new FlowBuilderCarter();
		carter.setStateIdsMap(stateIdsMap);
		carter.setStateNamesMap(stateNamesMap);
		carter.setTransitionIdsMap(transitionIdsMap);
		carter.setTransitionNamesMap(transitionNamesMap);
		carter.setDefaultInitialState(defaultInitialState);
		
		buildTransitionGraph(carter);
		buildStateGraph(carter);
		
		return carter;
	}
	
	public FlowBuilderCarter buildFlow(InputStream inputStream) throws FlowException {
		if(logger.isDebugEnabled()) {
			logger.debug("Create JAXB parser and marshaller from package");											
		}
		
		try {
			JAXBContext jc = JAXBContext.newInstance("cz.wicketstuff.boss.flow.builder.xml.jaxb");
			Unmarshaller um = jc.createUnmarshaller();

			if(logger.isDebugEnabled()) {
				logger.debug("Parsing XML with flowDescriptor");											
			}
			@SuppressWarnings("unchecked")
			JAXBElement<FlowDescriptorType> fdElement = (JAXBElement<FlowDescriptorType>) um.unmarshal(inputStream);		
			
			FlowBuilderCarter carter = buildFlow(fdElement.getValue());	
			
			return carter;
		} catch (JAXBException e) {
			throw new FlowException("Cannot build the flow", e);
		} 
	}

	public IFlowTree buildFlowTree(FlowBuilderCarter carter, Integer flowId, String flowName) throws FlowException {
		FlowTree tree = new FlowTree(flowId, flowName);
		Map<String, IFlowTransition> transitionNamesMap = new HashMap<String, IFlowTransition>(carter.getTransitionNamesMap().size());
		Map<Integer, IFlowTransition> transitionIdsMap = new HashMap<Integer, IFlowTransition>(carter.getTransitionIdsMap().size());
		Map<String, IFlowState> stateNamesMap = new HashMap<String, IFlowState>(carter.getStateNamesMap().size());
		Map<Integer, IFlowState> stateIdsMap = new HashMap<Integer, IFlowState>(carter.getStateIdsMap().size());
		tree.setTransitionNamesMap(transitionNamesMap);
		tree.setTransitionIdsMap(transitionIdsMap);
		tree.setStateNamesMap(stateNamesMap);
		tree.setStateIdsMap(stateIdsMap);
		tree.setDefaultInitialState(carter.getDefaultInitialState());
		
		for(StateCapsule sc : carter.getStateIdsMap().values()) {
			stateIdsMap.put(sc.getFlowState().getStateId(), sc.getFlowState());
		}
		for(StateCapsule sc : carter.getStateNamesMap().values()) {
			stateNamesMap.put(sc.getFlowState().getStateName(), sc.getFlowState());
		}
		
		for(TransitionCapsule sc : carter.getTransitionIdsMap().values()) {
			transitionIdsMap.put(sc.getFlowTransition().getTransitionId(), sc.getFlowTransition());
		}
		for(TransitionCapsule sc : carter.getTransitionNamesMap().values()) {
			transitionNamesMap.put(sc.getFlowTransition().getTransitionName(), sc.getFlowTransition());
		}
		return tree;
	} 
	
	@Override
	public IFlowTree buildFlowTree(InputStream inputStream, Integer flowId, String flowName) throws FlowException {
		return buildFlowTree(buildFlow(inputStream), flowId, flowName);
	}
	
	public FlowObjectFactory getFlowObjectFactory() {
		return flowObjectFactory;
	}

	public void setFlowObjectFactory(FlowObjectFactory flowObjectFactory) {
		this.flowObjectFactory = flowObjectFactory;
	}
	
	@Override
	protected void finalize() throws Throwable {
		flowObjectFactory = null;
		super.finalize();
	}

}
