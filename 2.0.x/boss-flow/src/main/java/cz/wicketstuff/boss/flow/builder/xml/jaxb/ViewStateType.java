//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.02 at 11:22:00 AM CEST 
//


package cz.wicketstuff.boss.flow.builder.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * The state is representing a view or a display content.
 * 				It is usually a page in MVC frameworks.
 * 			
 * 
 * <p>Java class for ViewStateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ViewStateType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}RealStateType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="viewName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ViewStateType", propOrder = {
    "viewName"
})
public class ViewStateType
    extends RealStateType
{

    protected String viewName;

    /**
     * Gets the value of the viewName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the value of the viewName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewName(String value) {
        this.viewName = value;
    }

}
