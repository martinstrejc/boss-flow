//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.02 at 12:14:08 AM CET 
//


package cz.wicketstuff.boss.flow.builder.xml.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cz.wicketstuff.boss.flow.builder.xml.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FlowDescriptor_QNAME = new QName("http://www.wicketstuff.cz/p/boss-flow/flowXmlModel", "flowDescriptor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cz.wicketstuff.boss.flow.builder.xml.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FlowDescriptorType }
     * 
     */
    public FlowDescriptorType createFlowDescriptorType() {
        return new FlowDescriptorType();
    }

    /**
     * Create an instance of {@link ViewStateType }
     * 
     */
    public ViewStateType createViewStateType() {
        return new ViewStateType();
    }

    /**
     * Create an instance of {@link VirtualStateType }
     * 
     */
    public VirtualStateType createVirtualStateType() {
        return new VirtualStateType();
    }

    /**
     * Create an instance of {@link JoinStateType }
     * 
     */
    public JoinStateType createJoinStateType() {
        return new JoinStateType();
    }

    /**
     * Create an instance of {@link ConditionStateType }
     * 
     */
    public ConditionStateType createConditionStateType() {
        return new ConditionStateType();
    }

    /**
     * Create an instance of {@link SwitchStateType }
     * 
     */
    public SwitchStateType createSwitchStateType() {
        return new SwitchStateType();
    }

    /**
     * Create an instance of {@link StateIdentifierType }
     * 
     */
    public StateIdentifierType createStateIdentifierType() {
        return new StateIdentifierType();
    }

    /**
     * Create an instance of {@link TransitionIdentifierType }
     * 
     */
    public TransitionIdentifierType createTransitionIdentifierType() {
        return new TransitionIdentifierType();
    }

    /**
     * Create an instance of {@link StateType }
     * 
     */
    public StateType createStateType() {
        return new StateType();
    }

    /**
     * Create an instance of {@link SwitchCaseType }
     * 
     */
    public SwitchCaseType createSwitchCaseType() {
        return new SwitchCaseType();
    }

    /**
     * Create an instance of {@link TransitionType }
     * 
     */
    public TransitionType createTransitionType() {
        return new TransitionType();
    }

    /**
     * Create an instance of {@link RealStateType }
     * 
     */
    public RealStateType createRealStateType() {
        return new RealStateType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlowDescriptorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wicketstuff.cz/p/boss-flow/flowXmlModel", name = "flowDescriptor")
    public JAXBElement<FlowDescriptorType> createFlowDescriptor(FlowDescriptorType value) {
        return new JAXBElement<FlowDescriptorType>(_FlowDescriptor_QNAME, FlowDescriptorType.class, null, value);
    }

}
