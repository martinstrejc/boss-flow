package cz.wicketstuff.boss.flow.model;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Category of flow step or transition. It is useful to sort steps or transitions to categories.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowCategory extends Serializable {
	
	/**
	 * Return the name of the category that is represented by the object implementing this interface.
	 * 
	 * @return the name of the category
	 */
	public String getCategoryName();
	
	/**
	 * Return true if the category name matches the regular expression.
	 * 
	 * @param regex regular expression to match
	 * @return true if the name matches the regex
	 */
	public boolean matches(String regex);
	
	/**
	 * Return true if the category name matches the pattern.
	 * 
	 * @param pattern matching pattern
	 * @return true if the name matches the pattern
	 */
	public boolean matches(Pattern pattern);
	

}
