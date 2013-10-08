package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

/**
 * @author Martin Strejc
 *
 */
public class EnumDescriptor implements Comparable<EnumDescriptor> {

	private String originalName;
	private String name;
	
	
	/**
	 * Default construcotr 
	 */
	public EnumDescriptor() {
		super();
	}

	/**
	 * @param name
	 */
	public EnumDescriptor(String name) {
		this(name, name);
	}

	/**
	 * @param originalName
	 * @param name
	 */
	public EnumDescriptor(String originalName, String name) {
		super();
		this.originalName = originalName;
		this.name = name;
	}
	
	/**
	 * @return the originalName
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @param originalName the originalName to set
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(EnumDescriptor o) {
		return name.compareTo(o.name);
	}
	
}
