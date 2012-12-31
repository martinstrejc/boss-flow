package cz.wicketstuff.boss.flow.test;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Martin Strejc
 *
 */
public class FlowFileResource {

	public static final String COMPLETE_FLOW_FILE = "test-flow-complete.xml";
	
	public FlowFileResource() {
	}

	private Logger log = LoggerFactory.getLogger(getClass().getName());
	
	protected InputStream getResourceAsStream(String name) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(name);
		if(is == null) {
			log.error("Required tested resource '" + name + "' doesn't exist! Check the resource location!");
			throw new IllegalStateException("Required tested resource '" + name + "' doesn't exist! Check the resource location!");
		}
		return is;
	}

	public String getCompleteFlowFileName() {
		return COMPLETE_FLOW_FILE;
	}
	
	public InputStream getCompleteFlowFileStream() {
		return getResourceAsStream(getCompleteFlowFileName());
	}

}
