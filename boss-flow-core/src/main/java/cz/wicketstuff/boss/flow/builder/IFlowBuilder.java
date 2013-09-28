package cz.wicketstuff.boss.flow.builder;

import java.io.InputStream;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.model.IFlowTree;

/**
 * Builder that can build the flow tree.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowBuilder {
	
	/**
	 * Build the flow tree from input. Each implementation uses another input to build flow.
	 * The input is usually an XML file.
	 * 
	 * @param inputStream
	 * @param flowId
	 * @param flowName
	 * @return flow tree
	 * @throws FlowException
	 */
	public IFlowTree buildFlowTree(InputStream inputStream, Integer flowId, String flowName) throws FlowException;

}
