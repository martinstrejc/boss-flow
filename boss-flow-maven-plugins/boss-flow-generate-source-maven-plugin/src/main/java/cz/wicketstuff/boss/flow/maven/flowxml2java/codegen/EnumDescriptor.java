package cz.wicketstuff.boss.flow.maven.flowxml2java.codegen;

/**
 * @author Martin Strejc
 *
 */
public class EnumDescriptor<T> implements Comparable<EnumDescriptor<T>> {

	private String originalName;
	private String name;
	private Integer orderId;
	private T object;
	
	
	/**
	 * Default construcotr 
	 */
	public EnumDescriptor() {
		super();
	}

	/**
	 * @param name
	 */
	public EnumDescriptor(String name, T object) {
		this(name, name, object);
	}

	/**
	 * @param originalName
	 * @param name
	 */
	public EnumDescriptor(String originalName, String name, T object) {
		super();
		this.originalName = originalName;
		this.name = name;
		this.object = object;
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

	/**
	 * @return the orderId
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * @return the object
	 */
	public T getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public int compareTo(EnumDescriptor<T> o) {
		return name.compareTo(o.name);
	}
	
	
	
	
	
}
