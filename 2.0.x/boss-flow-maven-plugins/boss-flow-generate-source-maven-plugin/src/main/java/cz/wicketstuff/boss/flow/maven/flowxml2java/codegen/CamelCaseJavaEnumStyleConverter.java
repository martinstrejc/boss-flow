package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

import java.util.regex.Pattern;

/**
 * Upper case Java style converter that converts to upper cases as enums are
 * usually written.
 * 
 * @author Martin Strejc
 * 
 */
public class CamelCaseJavaEnumStyleConverter implements IJavaStyleConverter {

	private Pattern PATTERN1 = Pattern.compile("([a-z])([A-Z])");
	private Pattern PATTERN2 = Pattern.compile("[^\\w^\\d^]+");

	public CamelCaseJavaEnumStyleConverter() {
	}

	@Override
	public String createJavaStyleName(String name) {
		return PATTERN2.matcher(
								PATTERN1.matcher(name).replaceAll("$1_$2")
				).replaceAll("_")
				.toUpperCase();
	}

	@Override
	protected void finalize() throws Throwable {
		PATTERN1 = null;
		PATTERN2 = null;
		super.finalize();
	}

}
