package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import java.util.regex.Pattern;

/**
 * Upper case Java style converter that converts to upper cases as enums are usually written.
 * 
 * @author Martin Strejc
 *
 */
public class UpperCaseJavaStyleConverter implements IJavaStyleConverter {

	private Pattern pattern = Pattern.compile("[^\\w^\\d^]+");
	
	public UpperCaseJavaStyleConverter() {
	}

	@Override
	public String createJavaStyleName(String name) {
		return pattern.matcher(name).replaceAll("_").toUpperCase();
	}
	
	@Override
	protected void finalize() throws Throwable {
		pattern = null;
		super.finalize();
	}

}
