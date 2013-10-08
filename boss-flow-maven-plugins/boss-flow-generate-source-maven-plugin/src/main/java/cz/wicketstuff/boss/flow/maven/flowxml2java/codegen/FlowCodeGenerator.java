package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.builder.xml.FlowBuilderCarter;
import cz.wicketstuff.boss.flow.builder.xml.JaxbFlowBuilder;
import cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml;

/**
 * @author Martin Strejc
 *
 */
public class FlowCodeGenerator {

	JaxbFlowBuilder flowBuilder;
	
	public FlowCodeGenerator() {
		flowBuilder = JaxbFlowBuilder.newInstance();
	}
	
	public void generate(FlowXml flowXml, FlowBuilderCarter flowBuilderCarter) {
		try {
			JCodeModel codeModel = new JCodeModel();
			JPackage flowPackage = codeModel._package(flowXml.getPackageName());
			
			JDefinedClass stateEnum = flowPackage._enum(flowXml.getStateEnumName());
			stateEnum.javadoc().append("Flow states defined in '" + flowXml.getId() + "'");
			
			JDefinedClass transitionEnum = flowPackage._enum(flowXml.getTransitionEnumName());
			transitionEnum.javadoc().append("Flow transitions defined in '" + flowXml.getId() + "'");
			
			
			
			stateEnum.enumConstant("S1");
			
			transitionEnum.enumConstant("t1");
			
			codeModel.build(new File("."), System.out);
			
		} catch (JClassAlreadyExistsException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected List<EnumDescriptor> fillEnumMap(Map<String, ?> map) {
		List<EnumDescriptor> list = new ArrayList<>(map.size());
		for(String name : map.keySet()) {
			list.add(new EnumDescriptor(name));
		}
		return list;
	}

	protected void javaStyleNames(List<EnumDescriptor> list) {
		IJavaStyleConverter converter = new UpperCaseJavaStyleConverter();
		Map<String, Integer> names = new HashMap<String, Integer>(list.size());
//		Set<String> finalNames = new HashSet<String>(list.size());
		for(EnumDescriptor el : list) {
			String name = converter.createJavaStyleName(el.getName());
			Integer i = names.put(name, 0);
			if(i == null) {
				el.setName(name);				
			} else {
				i++;
				String fixedName = appendNameSuffix(name, i);					
				el.setName(fixedName);
			}
		}
	}
	
	/**
	 * Create numbered suffix eg. appendNameSuffix("VALUE, 1") produces "VALUE_01"
	 * 
	 * @param name
	 * @param suffix can be <code>null</code>, returns name
	 * @return
	 */
	protected String appendNameSuffix(String name, Integer suffix) {
		if(suffix == null) {
			return name;
		}
		if(suffix > 9) {
			return name + "_" + suffix;
		} else {
			return name + "_0" + suffix;	
		}
	}

	protected FlowBuilderCarter buildFlow(File file) throws FlowException, FileNotFoundException {
		return flowBuilder.buildFlow(new FileInputStream(file));
	}
	
	protected FlowBuilderCarter buildFlow(InputStream inputStream) throws FlowException {
		return flowBuilder.buildFlow(inputStream);
	}

}
