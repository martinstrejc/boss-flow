package cz.wicketstuff.boss.flow.maven.flowxml2java;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;

/**
 * @author Martin Strejc
 *
 */
@Mojo(name="bossFlowXml2java", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
@Execute(phase=LifecyclePhase.GENERATE_SOURCES)
public class FlowXml2JavaMojo extends AbstractMojo {

	/**
	 * Default constructor from superclass 
	 */
	public FlowXml2JavaMojo() {
		super();
	}
	
	@Parameter
	String id;

	@Parameter(required=true)
	File xmlFile;

	@Parameter
	String packageName;
	
	@Parameter
	String transitionEnumName;
	
	@Parameter
	String stateEnumName;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		FlowXml flowXml = new FlowXml();
		flowXml.setPackageName(packageName);
		flowXml.setStateEnumName(stateEnumName);
		flowXml.setTransitionEnumName(transitionEnumName);
		flowXml.setXmlFile(xmlFile);
	}
	
	protected void generate(FlowXml flowXml) throws MojoExecutionException, MojoFailureException {
		if(!checkParameters(flowXml)) {
			error("Skipping generation " + flowXml.id);
			return;
		}
		
		try {
			JCodeModel codeModel = new JCodeModel();
			JPackage flowPackage = codeModel._package(flowXml.packageName);
			
			JDefinedClass stateEnum = flowPackage._enum(flowXml.stateEnumName);
			stateEnum.javadoc().append("Flow states defined in '" + flowXml.id + "'");
			
			JDefinedClass transitionEnum = flowPackage._enum(flowXml.transitionEnumName);
			transitionEnum.javadoc().append("Flow transitions defined in '" + flowXml.id + "'");
			
			stateEnum.enumConstant("S1");
			
			transitionEnum.enumConstant("t1");
			
			codeModel.build(new File("."), System.out);
			
		} catch (JClassAlreadyExistsException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected boolean checkParameters(FlowXml flowXml) {
		File file = flowXml.xmlFile;
		if(file == null) {
			error("Parameter flowXml.xmlFile is missing.");
			return false;
		}
		if(!file.exists()) {
			error("File '" + file.getAbsolutePath() + "' doesn't exist.");
			return false;
		}
		return true;
	}
	
	protected void error(String message) {
		getLog().error(message);
	}


}
