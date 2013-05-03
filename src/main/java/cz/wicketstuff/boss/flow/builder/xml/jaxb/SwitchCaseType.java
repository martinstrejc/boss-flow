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
 * Similar to 'case' statement of 'switch' statement in
 * 				java programming language.
 * 			
 * 
 * <p>Java class for SwitchCaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SwitchCaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transition" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}TransitionIdentifierType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="caseValue" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwitchCaseType", propOrder = {
    "transition"
})
public class SwitchCaseType {

    protected TransitionIdentifierType transition;
    @XmlAttribute(name = "caseValue", required = true)
    protected String caseValue;

    /**
     * Gets the value of the transition property.
     * 
     * @return
     *     possible object is
     *     {@link TransitionIdentifierType }
     *     
     */
    public TransitionIdentifierType getTransition() {
        return transition;
    }

    /**
     * Sets the value of the transition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitionIdentifierType }
     *     
     */
    public void setTransition(TransitionIdentifierType value) {
        this.transition = value;
    }

    /**
     * Gets the value of the caseValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseValue() {
        return caseValue;
    }

    /**
     * Sets the value of the caseValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseValue(String value) {
        this.caseValue = value;
    }

}
