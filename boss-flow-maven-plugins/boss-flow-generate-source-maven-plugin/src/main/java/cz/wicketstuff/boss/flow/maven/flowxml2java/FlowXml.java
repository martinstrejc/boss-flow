package cz.wicketstuff.boss.flow.maven.flowxml2java;

import java.io.File;

/**
 * @author Martin Strejc
 *
 */
public class FlowXml {

	String id;

	File xmlFile;

	String packageName;
	
	String transitionEnumName;
	
	String stateEnumName;
	
	/**
	 * Default constructor 
	 */
	public FlowXml() {
		super();
	}

	/**
	 * @return the xmlFile
	 */
	public File getXmlFile() {
		return xmlFile;
	}

	/**
	 * @param xmlFile the xmlFile to set
	 */
	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the transitionEnumName
	 */
	public String getTransitionEnumName() {
		return transitionEnumName;
	}

	/**
	 * @param transitionEnumName the transitionEnumName to set
	 */
	public void setTransitionEnumName(String transitionEnumName) {
		this.transitionEnumName = transitionEnumName;
	}

	/**
	 * @return the stateEnumName
	 */
	public String getStateEnumName() {
		return stateEnumName;
	}

	/**
	 * @param stateEnumName the stateEnumName to set
	 */
	public void setStateEnumName(String stateEnumName) {
		this.stateEnumName = stateEnumName;
	}
	
	

}
