package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;

import cz.wicketstuff.boss.flow.FlowException;
import cz.wicketstuff.boss.flow.builder.xml.FlowBuilderCarter;
import cz.wicketstuff.boss.flow.builder.xml.JaxbFlowBuilder;
import cz.wicketstuff.boss.flow.builder.xml.StateCapsule;
import cz.wicketstuff.boss.flow.builder.xml.TransitionCapsule;
import cz.wicketstuff.boss.flow.maven.flowxml2java.FlowXml;

/**
 * @author Martin Strejc
 *
 */
public class FlowCodeGenerator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private JaxbFlowBuilder flowBuilder;
	
	public FlowCodeGenerator() {
		flowBuilder = JaxbFlowBuilder.newInstance();
	}
	public void generate(List<FlowXml> flowXmls) throws FileNotFoundException, FlowException {
		for(FlowXml flowXml : flowXmls) {
			generate(flowXml, buildFlow(flowXml.getXmlFile()));
		}
	}
	
	public void generate(FlowXml flowXml, FlowBuilderCarter flowBuilderCarter) {
		try {
			JCodeModel codeModel = new JCodeModel();
			JPackage flowPackage = codeModel._package(flowXml.getPackageName());
			
			Map<String, StateCapsule> stateMap = flowBuilderCarter.getStateNamesMap();
			Map<String, TransitionCapsule> transitionMap = flowBuilderCarter.getTransitionNamesMap();
			
			List<EnumDescriptor<StateCapsule>> stateEnumList = fillEnumMap(stateMap);
			List<EnumDescriptor<TransitionCapsule>> transitionEnumList = fillEnumMap(transitionMap);
			
			javaStyleNames(stateEnumList);
			javaStyleNames(transitionEnumList);
			
			JDefinedClass stateEnum = flowPackage._enum(flowXml.getStateEnumName());
			stateEnum.javadoc().append("Flow states defined in '" + flowXml.getId() + "'");

			JFieldVar field = stateEnum.field(JMod.PRIVATE & JMod.FINAL, String.class, "originalName");
			field.javadoc().append("The original name in XML");
			
			JMethod constructor = stateEnum.constructor(JMod.PRIVATE);
			constructor.param(String.class, "originalName");
			constructor.body().assign(JExpr.refthis("originalName"), field);
			
			for(EnumDescriptor<StateCapsule> e : stateEnumList) {
				JEnumConstant enumConst = stateEnum.enumConstant(e.getName());
				String originalName = e.getOriginalName();
				Integer orderId = e.getOrderId();				
				enumConst.arg(JExpr.lit(originalName));
				
				if(orderId == null || orderId == 0) {
					enumConst.javadoc().append(originalName);					
				} else {
					enumConst.javadoc().append(originalName + " - order " + orderId);
				}
			}
			
			JDefinedClass transitionEnum = flowPackage._enum(flowXml.getTransitionEnumName());
			transitionEnum.javadoc().append("Flow transitions defined in '" + flowXml.getId() + "'");
			
			for(EnumDescriptor<TransitionCapsule> e : transitionEnumList) {
				JEnumConstant enumConst = transitionEnum.enumConstant(e.getName());
				Integer orderId = e.getOrderId();
				String originalName = e.getOriginalName();
				if(orderId == null || orderId == 0) {
					enumConst.javadoc().append(originalName);					
				} else {
					enumConst.javadoc().append(originalName + " - order " + orderId);
				}
			}
			
			codeModel.build(new File("."), System.out);
			
		} catch (JClassAlreadyExistsException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected <T> List<EnumDescriptor<T>> fillEnumMap(Map<String, T> map) {
		List<EnumDescriptor<T>> list = new ArrayList<>(map.size());
		for( Map.Entry<String, T> e : map.entrySet()) {
			list.add(new EnumDescriptor<T>(e.getKey(), e.getValue()));
		}
		return list;
	}

	protected <T> void javaStyleNames(List<EnumDescriptor<T>> list) {
		IJavaStyleConverter converter = new UpperCaseJavaStyleConverter();
		Map<String, EnumDescriptor<T>> names = new HashMap<String, EnumDescriptor<T>>(list.size());
		for(EnumDescriptor<T> e : list) {
			String name = stripSuffix(converter.createJavaStyleName(e.getName()));
			EnumDescriptor<T> stored = names.put(name, e);
			if(stored == null) {
				e.setName(name);
			} else {
				Integer orderId = stored.getOrderId();
				if(orderId == null) {
					orderId = 1;
					stored.setName(appendNameSuffix(name, orderId));
				}
				orderId++;	
				e.setName(appendNameSuffix(name, orderId));
				e.setOrderId(orderId);
			}
		}
	}
	
	private static final Pattern SUFFIX_PATTERN = Pattern.compile("^(.*)(_[\\d]{2})$");
	
	/**
	 * String suffix stripSuffix("VALUE_01") produces "VALUE"
	 * 
	 * @param name
	 * @return
	 */
	public String stripSuffix(String name) {
		return SUFFIX_PATTERN.matcher(name).replaceAll("$1");
	}
	
	/**
	 * Create numbered suffix e.g. appendNameSuffix("VALUE, 1") produces "VALUE_01"
	 * 
	 * @param name
	 * @param suffix can be <code>null</code>, returns name
	 * @return
	 */
	public String appendNameSuffix(String name, Integer suffix) {
		if(suffix == null) {
			return name;
		}
		if(suffix > 9) {
			return name + "_" + suffix;
		} else {
			return name + "_0" + suffix;	
		}
	}

	public FlowBuilderCarter buildFlow(File file) throws FlowException, FileNotFoundException {
		return flowBuilder.buildFlow(new FileInputStream(file));
	}
	
	public FlowBuilderCarter buildFlow(InputStream inputStream) throws FlowException {
		return flowBuilder.buildFlow(inputStream);
	}

}
