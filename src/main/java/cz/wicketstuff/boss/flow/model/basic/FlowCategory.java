/**
 * 
 */
package cz.wicketstuff.boss.flow.model.basic;

import java.util.regex.Pattern;

import cz.wicketstuff.boss.flow.model.IFlowCategory;

/**
 * @author Martin Strejc
 *
 */
public class FlowCategory implements IFlowCategory {

	private static final long serialVersionUID = 1L;
	
	private String categoryName;

	public FlowCategory() {
	}

	public FlowCategory(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public boolean matches(String regex) {
		if(regex == null) {
			return true;
		}
		if(categoryName == null) {
			return false;
		}
		return categoryName.matches(regex);
	}

	@Override
	public boolean matches(Pattern pattern) {
		if(pattern == null) {
			return true;
		}
		if(categoryName == null) {
			return false;
		}
		return pattern.matcher(categoryName).matches();
	}
	

}
