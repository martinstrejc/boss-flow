package cz.wicketstuff.boss.flow.util;

import java.util.List;
import java.util.regex.Pattern;

import cz.wicketstuff.boss.flow.model.IFlowCategorized;
import cz.wicketstuff.boss.flow.model.IFlowCategory;

/**
 * @author Martin Strejc
 *
 */
public final class FlowMatcherHelper {

	private FlowMatcherHelper() {
	}
	
	public static boolean matchesAny(IFlowCategorized categorized, String regex) {
		if(regex == null) {
			return true;
		}
		List<IFlowCategory> categories = categorized.getFlowCategories();
		if(categories == null || categories.size() == 0) {
			return false;
		}
		for(IFlowCategory category : categories) {
			if(category.matches(regex)) {
				return true;
			}
		}
		return false;
	}

	public static boolean matchesAny(IFlowCategorized categorized, Pattern pattern) {
		if(pattern == null) {
			return true;
		}
		List<IFlowCategory> categories = categorized.getFlowCategories();
		if(categories == null || categories.size() == 0) {
			return false;
		}
		for(IFlowCategory category : categories) {
			if(category.matches(pattern)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean matchesNone(IFlowCategorized categorized, String regex) {
		if(regex == null) {
			return false;
		}
		List<IFlowCategory> categories = categorized.getFlowCategories();
		if(categories == null || categories.size() == 0) {
			return true;
		}
		for(IFlowCategory category : categories) {
			if(category.matches(regex)) {
				return false;
			}
		}
		return true;
	}
	
	public static Pattern strigToPattern(String regex) {
		return regex == null ? null : Pattern.compile(regex);
	}

	public static boolean matchesNone(IFlowCategorized categorized, Pattern pattern) {
		if(pattern == null) {
			return false;
		}
		List<IFlowCategory> categories = categorized.getFlowCategories();
		if(categories == null || categories.size() == 0) {
			return true;
		}
		for(IFlowCategory category : categories) {
			if(category.matches(pattern)) {
				return false;
			}
		}
		return true;
	}

}
