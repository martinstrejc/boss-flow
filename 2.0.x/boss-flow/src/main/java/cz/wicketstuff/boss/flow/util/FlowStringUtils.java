package cz.wicketstuff.boss.flow.util;

import java.util.regex.Pattern;

/**
 * @author Martin Strejc
 *
 */
public final class FlowStringUtils {

	public static final String DEFAULT_EMPTY_STRING = "";
	public static final Pattern EMPTY_STRING_PATTERN = Pattern.compile("\\s*");
	
	private FlowStringUtils() {
	}
	
	public static String safeToString(Object o) {
		return safeToString(o, null);
	}

	public static String safeToEmptyString(Object o) {
		return safeToString(o, DEFAULT_EMPTY_STRING);
	}

	public static String safeToString(Object o, String defaultValue) {
		return o == null ? defaultValue : o.toString();
	}

	public static boolean isEmpty(Object o) {
		return isEmpty(safeToString(o));
	}

	public static boolean isEmpty(String text) {
		return text == null ? true : DEFAULT_EMPTY_STRING.equals(text);
	}
	
	public static boolean isBlank(Object o) {
		return isBlank(safeToString(o));
	}

	public static boolean isBlank(String text) {
		return text == null ? true : EMPTY_STRING_PATTERN.matcher(text).matches();
	}

}
