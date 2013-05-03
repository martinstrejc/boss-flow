//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.03 at 11:50:19 PM CEST 
//


package cz.wicketstuff.boss.flow.builder.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * The transition element. This element describe a
 * 				transition. Each transition is mandatory described by name and
 * 				target state. ID is optional, it should be autogenerated.
 * 			
 * 
 * <p>Java class for TransitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransitionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}TargetRefIdentifierAttributeGroup"/>
 *       &lt;attGroup ref="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}IdentifierAttributeGroup"/>
 *       &lt;attribute name="hitCountable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransitionType")
public class TransitionType {

    @XmlAttribute(name = "hitCountable")
    protected Boolean hitCountable;
    @XmlAttribute(name = "targetStateName", required = true)
    protected String targetStateName;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "id")
    protected Integer id;

    /**
     * Gets the value of the hitCountable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isHitCountable() {
        if (hitCountable == null) {
            return true;
        } else {
            return hitCountable;
        }
    }

    /**
     * Sets the value of the hitCountable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHitCountable(Boolean value) {
        this.hitCountable = value;
    }

    /**
     * Gets the value of the targetStateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetStateName() {
        return targetStateName;
    }

    /**
     * Sets the value of the targetStateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetStateName(String value) {
        this.targetStateName = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

}
