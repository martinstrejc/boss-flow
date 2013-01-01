//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.01 at 10:56:37 PM CET 
//


package cz.wicketstuff.boss.flow.builder.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Similar to 'switch' statement in java programming
 * 				language.
 * 			
 * 
 * <p>Java class for SwitchStateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SwitchStateType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}VirtualStateType">
 *       &lt;sequence>
 *         &lt;element name="switchExpression" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="defaultTransition" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}TransitionIdentifierType"/>
 *         &lt;element name="switchCase" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}SwitchCaseType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwitchStateType", propOrder = {
    "switchExpression",
    "defaultTransition",
    "switchCase"
})
public class SwitchStateType
    extends VirtualStateType
{

    @XmlElement(required = true)
    protected String switchExpression;
    @XmlElement(required = true)
    protected TransitionIdentifierType defaultTransition;
    protected List<SwitchCaseType> switchCase;

    /**
     * Gets the value of the switchExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwitchExpression() {
        return switchExpression;
    }

    /**
     * Sets the value of the switchExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwitchExpression(String value) {
        this.switchExpression = value;
    }

    /**
     * Gets the value of the defaultTransition property.
     * 
     * @return
     *     possible object is
     *     {@link TransitionIdentifierType }
     *     
     */
    public TransitionIdentifierType getDefaultTransition() {
        return defaultTransition;
    }

    /**
     * Sets the value of the defaultTransition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitionIdentifierType }
     *     
     */
    public void setDefaultTransition(TransitionIdentifierType value) {
        this.defaultTransition = value;
    }

    /**
     * Gets the value of the switchCase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the switchCase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSwitchCase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SwitchCaseType }
     * 
     * 
     */
    public List<SwitchCaseType> getSwitchCase() {
        if (switchCase == null) {
            switchCase = new ArrayList<SwitchCaseType>();
        }
        return this.switchCase;
    }

}
