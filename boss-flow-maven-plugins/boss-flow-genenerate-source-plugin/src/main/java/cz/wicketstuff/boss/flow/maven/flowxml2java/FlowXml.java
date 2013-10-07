package cz.wicketstuff.boss.flow.maven.flowxml2java;

import java.io.File;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author Martin Strejc
 *
 */
public class FlowXml {

	@Parameter(defaultValue="")
	String id;

	@Parameter(required=true)
	File xmlFile;

	@Parameter(defaultValue="bossFlow.generated")
	String packageName;
	
	@Parameter(defaultValue="FlowTransitionEnum")
	String transitionEnumName;
	
	@Parameter(defaultValue="FlowStateEnum")
	String stateEnumName;
	
	/**
	 * Default constructor 
	 */
	public FlowXml() {
		super();
	}
	
}
