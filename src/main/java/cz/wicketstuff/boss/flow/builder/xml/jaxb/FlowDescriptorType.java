//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.02 at 12:14:08 AM CET 
//


package cz.wicketstuff.boss.flow.builder.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * Main element describing whole flow.
 * 
 * <p>Java class for FlowDescriptorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlowDescriptorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flowId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flowName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flowDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="state" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}StateType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="realState" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}RealStateType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="viewState" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}ViewStateType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="virtualState" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}VirtualStateType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="joinState" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}JoinStateType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="conditionState" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}ConditionStateType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="switchState" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}SwitchStateType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="transition" type="{http://www.wicketstuff.cz/p/boss-flow/flowXmlModel}TransitionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlowDescriptorType", propOrder = {
    "flowId",
    "flowName",
    "flowDescription",
    "stateOrRealStateOrViewState"
})
public class FlowDescriptorType {

    protected int flowId;
    @XmlElement(required = true)
    protected String flowName;
    protected String flowDescription;
    @XmlElements({
        @XmlElement(name = "state", type = StateType.class),
        @XmlElement(name = "realState", type = RealStateType.class),
        @XmlElement(name = "viewState", type = ViewStateType.class),
        @XmlElement(name = "virtualState", type = VirtualStateType.class),
        @XmlElement(name = "joinState", type = JoinStateType.class),
        @XmlElement(name = "conditionState", type = ConditionStateType.class),
        @XmlElement(name = "switchState", type = SwitchStateType.class),
        @XmlElement(name = "transition", type = TransitionType.class)
    })
    protected List<Object> stateOrRealStateOrViewState;

    /**
     * Gets the value of the flowId property.
     * 
     */
    public int getFlowId() {
        return flowId;
    }

    /**
     * Sets the value of the flowId property.
     * 
     */
    public void setFlowId(int value) {
        this.flowId = value;
    }

    /**
     * Gets the value of the flowName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowName() {
        return flowName;
    }

    /**
     * Sets the value of the flowName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowName(String value) {
        this.flowName = value;
    }

    /**
     * Gets the value of the flowDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowDescription() {
        return flowDescription;
    }

    /**
     * Sets the value of the flowDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowDescription(String value) {
        this.flowDescription = value;
    }

    /**
     * Gets the value of the stateOrRealStateOrViewState property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateOrRealStateOrViewState property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateOrRealStateOrViewState().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StateType }
     * {@link RealStateType }
     * {@link ViewStateType }
     * {@link VirtualStateType }
     * {@link JoinStateType }
     * {@link ConditionStateType }
     * {@link SwitchStateType }
     * {@link TransitionType }
     * 
     * 
     */
    public List<Object> getStateOrRealStateOrViewState() {
        if (stateOrRealStateOrViewState == null) {
            stateOrRealStateOrViewState = new ArrayList<Object>();
        }
        return this.stateOrRealStateOrViewState;
    }

}
