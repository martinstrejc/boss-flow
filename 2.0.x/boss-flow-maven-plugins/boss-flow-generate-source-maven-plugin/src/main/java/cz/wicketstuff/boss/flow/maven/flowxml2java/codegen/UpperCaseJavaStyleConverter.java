package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import java.util.regex.Pattern;

/**
 * Upper case Java style converter that converts to upper cases as enums are usually written.
 * 
 * @author Martin Strejc
 *
 */
public class UpperCaseJavaStyleConverter implements IJavaStyleConverter {

	private Pattern PATTERN = Pattern.compile("[^\\w^\\d^]+");
	
	public UpperCaseJavaStyleConverter() {
	}

	@Override
	public String createJavaStyleName(String name) {
		return PATTERN.matcher(name).replaceAll("_").toUpperCase();
	}
	
	@Override
	protected void finalize() throws Throwable {
		PATTERN = null;
		super.finalize();
	}

}
