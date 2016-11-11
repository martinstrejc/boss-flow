package cz.wicketstuff.boss.flow.maven.flowxml2java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.FlowCodeGenerator;
import cz.wicketstuff.boss.flow.maven.flowxml2java.codegen.JavaCodeHelper;

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
	
	/**
	 * Source directory where to store generated classes 
	 */
	@Parameter(required=true)
	private File sourceRoot;
	
	/**
	 * Flow XML configuration descriptor 
	 */
	@Parameter(required=true)
	private FlowXml[] flowXmls;
	
	@Parameter(defaultValue="cz.wicketstuff.boss.flow.generated")
	private String defaultPackageName;

	@Parameter(defaultValue="StateEnum")
	private String defaultStateEnumName;

	@Parameter(defaultValue="TransitionEnum")
	private String defaultTransitionEnumName;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if(sourceRoot == null) {
			error("Missing configuration parameter 'sourceRoot'.");
			throw new MojoExecutionException("Missing configuration parameter 'sourceRoot'.");
		}
		if(flowXmls == null || flowXmls.length == 0) {
			warn("Configuration parameter 'flowXmls' is empty, nothing to do!");
		} else {
			List<FlowXml> flowXmlList = new ArrayList<>();
			for(FlowXml flowXml : flowXmls) {
				completeParameters(flowXml);
				if(checkParameters(flowXml)) {
					flowXmlList.add(flowXml);
				} else {
					error("Cannot process flowXml");
				}
			}
			try {
				new FlowCodeGenerator().generate(flowXmlList);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FlowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void completeParameters(FlowXml flowXml) {
		if(flowXml.getPackageName() == null) {
			flowXml.setPackageName(defaultPackageName);
		}
		if(flowXml.getStateEnumName() == null) {
			flowXml.setStateEnumName(defaultStateEnumName);
		}
		if(flowXml.getTransitionEnumName() == null) {
			flowXml.setTransitionEnumName(defaultTransitionEnumName);
		}
	}
	
	protected void generate(FlowXml flowXml) throws MojoExecutionException, MojoFailureException {
		if(!checkParameters(flowXml)) {
			error("Skipping generation " + flowXml.getId());
			return;
		}
		
	}
	
	protected boolean checkParameters(FlowXml flowXml) {
		File file = flowXml.getXmlFile();
		if(file == null) {
			error("Parameter flowXml.xmlFile is missing.");
			return false;
		}
		if(!file.exists()) {
			error("File '" + file.getAbsolutePath() + "' doesn't exist.");
			return false;
		}
		if(!JavaCodeHelper.checkJavaPackageName(flowXml.getPackageName())) {
			error("Error in configuration flowXml id = '" + flowXml.getId() + "' says package name '" + flowXml.getPackageName() + "' is not acceptable, check if it matches " + JavaCodeHelper.PACKAGE_NAME_PATTERN.pattern());
		}
		if(!JavaCodeHelper.checkJavaClassName(flowXml.getStateEnumName())) {
			error("Error in configuration flowXml id = '" + flowXml.getId() + "' says state enum name '" + flowXml.getStateEnumName() + "' is not acceptable, check if it matches " + JavaCodeHelper.CLASS_NAME_PATTERN.pattern());
		}
		if(!JavaCodeHelper.checkJavaClassName(flowXml.getTransitionEnumName())) {
			error("Error in configuration flowXml id = '" + flowXml.getId() + "' says transition enum name '" + flowXml.getTransitionEnumName() + "' is not acceptable, check if it matches " + JavaCodeHelper.CLASS_NAME_PATTERN.pattern());
		}
		return true;
	}
	
	protected void error(String message) {
		getLog().error(message);
	}

	protected void warn(String message) {
		getLog().warn(message);
	}

}
