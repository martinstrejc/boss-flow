package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import java.util.regex.Pattern;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

/**
 * Helper method for checking or building java code
 * 
 * @author Martin Strejc
 *
 */
public final class JavaCodeHelper {

	public static final Pattern PACKAGE_NAME_PATTERN = Pattern.compile("([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");
	public static final Pattern CLASS_NAME_PATTERN = Pattern.compile("([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");
	
	private JavaCodeHelper() {
	}

	/**
	 * Check if the package name is java style package name e.g. com.example.mypackage
	 * 
	 * @param packageName
	 * @return <code>true</code> if the package name is correct
	 */
	public static boolean checkJavaPackageName(String packageName) {
		if(packageName == null) {
			return false;
		}
		return PACKAGE_NAME_PATTERN.matcher(packageName).matches();
	}
	
	/**
	 * Check if the class name is java style class name e.g. MyExampleClass
	 * 
	 * @param className
	 * @return <code>true</code> if the class name is correct
	 */
	public static boolean checkJavaClassName(String className) {
		if(className == null) {
			return false;
		}
		return CLASS_NAME_PATTERN.matcher(className).matches();
	}
	
	public static String entityNameToGetter(Class<?> entityClass, String entityName) {
		String firstLetter = entityName.substring(0, 1).toUpperCase();
		String other = entityName.substring(1);
		if(boolean.class.equals(entityClass)) {
			return "is" + firstLetter + other;
		} else {
			return "get" + firstLetter + other;
		}
	}
	
	/**
	 * Create entity of enum and add its read only final field and its getter.
	 * 
	 * @param definedClass
	 * @param constructor
	 * @param entityClass
	 * @param entityName
	 * @param entityDescription
	 * @return parameter added to constructor
	 */
	public static JVar entityEnumClass(JDefinedClass definedClass, JMethod constructor, Class<?> entityClass, String entityName, String entityDescription) {
		// add entity field
		JFieldVar field = definedClass.field(JMod.PRIVATE | JMod.FINAL, entityClass, entityName);
		field.javadoc().append(entityDescription);
		
		// add constructor parameter
		JVar param = constructor.param(entityClass, entityName);
		constructor.javadoc().addParam(param).append(entityDescription);
		constructor.body().assign(JExpr.refthis(entityDescription), field);		
		
		// add getter
		JMethod getter = definedClass.method(JMod.PUBLIC, entityClass, JavaCodeHelper.entityNameToGetter(entityClass, entityName));
		getter.javadoc().append("Gets " + entityDescription);
		getter.javadoc().addReturn().append(entityName);
		getter.body()._return(field);

		return param;
	}
	
}
