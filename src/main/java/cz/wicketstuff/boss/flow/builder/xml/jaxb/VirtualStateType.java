//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.03 at 11:50:19 PM CEST 
//


package cz.wicketstuff.boss.flow.builder.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * The state is a virtual state. It mean flow cannot
 * 				stand by this state or it has no sense to stand by. It is usually a
 * 				condition state that just redirect flow to another state that can be
 * 				also real or virtual.
 * 			
 * 
 * <p>Java class for VirtualStateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualStateType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}StateType">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualStateType")
@XmlSeeAlso({
    JoinStateType.class,
    ConditionStateType.class,
    SwitchStateType.class
})
public class VirtualStateType
    extends StateType
{


}
