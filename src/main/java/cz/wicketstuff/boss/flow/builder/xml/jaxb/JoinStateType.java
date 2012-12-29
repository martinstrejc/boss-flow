//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.12.29 at 11:31:07 PM CET 
//


package cz.wicketstuff.boss.flow.builder.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A virtual state that just joins many transitions into
 * 				once. It is used to redirect to another state. This state is useful
 * 				to add state or transition listeners or when refactoring of flow is
 * 				expected and it is more easy to change just one target.
 * 			
 * 
 * <p>Java class for JoinStateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JoinStateType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}VirtualStateType">
 *       &lt;sequence>
 *         &lt;element name="nextTransition" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}TransitionIdentifierType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JoinStateType", propOrder = {
    "nextTransition"
})
public class JoinStateType
    extends VirtualStateType
{

    @XmlElement(required = true)
    protected TransitionIdentifierType nextTransition;

    /**
     * Gets the value of the nextTransition property.
     * 
     * @return
     *     possible object is
     *     {@link TransitionIdentifierType }
     *     
     */
    public TransitionIdentifierType getNextTransition() {
        return nextTransition;
    }

    /**
     * Sets the value of the nextTransition property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitionIdentifierType }
     *     
     */
    public void setNextTransition(TransitionIdentifierType value) {
        this.nextTransition = value;
    }

}
