package cz.wicketstuff.boss.flow.model;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Interface that marks that an flow object is categorized by categories.
 * 
 * @author Martin Strejc
 *
 */
public interface IFlowCategorized {

	/**
	 * Return all categories that this object joins to. 
	 * It it not recommended to change this list, 
	 * if you are not sure about the implementation.
	 * Specially if the implementation is mutable or imutable list.
	 * Even it should be a serialized list.
	 * 
	 * @return the list of the categories. 
	 */
	public List<IFlowCategory> getFlowCategories();

	/**
	 * Return true if object is joined to any of categories matched by the regular expression.
	 * 
	 * @param regex regular expression to match
	 * @return true if any of categories matches the regex
	 */
	public boolean matchesAny(String regex);
	
	/**
	 * Return true if object is joined to any of categories matched by the regular expression.
	 * 
	 * @param pattern matching pattern
	 * @return true if any of categories matches the pattern
	 */
	public boolean matchesAny(Pattern pattern);
	
	/**
	 * Return true if object is NOT joined to any of categories matched by the regular expression.
	 * 
	 * @param regex regular expression to match
	 * @return true if any of categories matches the regex
	 */
	public boolean matchesNone(String regex);
	
	/**
	 * Return true if object is NOT joined to any of categories matched by the regular expression.
	 * 
	 * @param pattern matching pattern
	 * @return true if NONE of categories matches the pattern
	 */
	public boolean matchesNone(Pattern pattern);
	
}
