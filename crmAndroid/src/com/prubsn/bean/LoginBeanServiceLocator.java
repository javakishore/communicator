/**
 * LoginBeanServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.prubsn.bean;

public class LoginBeanServiceLocator extends org.apache.axis.client.Service implements com.prubsn.bean.LoginBeanService {

    public LoginBeanServiceLocator() {
    }


    public LoginBeanServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoginBeanServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LoginBean
    private java.lang.String LoginBean_address = "https://takafulnetuat.prubsn.com.my/PruBSNLogin/services/LoginBean";

    public java.lang.String getLoginBeanAddress() {
        return LoginBean_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LoginBeanWSDDServiceName = "LoginBean";

    public java.lang.String getLoginBeanWSDDServiceName() {
        return LoginBeanWSDDServiceName;
    }

    public void setLoginBeanWSDDServiceName(java.lang.String name) {
        LoginBeanWSDDServiceName = name;
    }

    public com.prubsn.bean.LoginBean getLoginBean() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LoginBean_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLoginBean(endpoint);
    }

    public com.prubsn.bean.LoginBean getLoginBean(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.prubsn.bean.LoginBeanSoapBindingStub _stub = new com.prubsn.bean.LoginBeanSoapBindingStub(portAddress, this);
            _stub.setPortName(getLoginBeanWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLoginBeanEndpointAddress(java.lang.String address) {
        LoginBean_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.prubsn.bean.LoginBean.class.isAssignableFrom(serviceEndpointInterface)) {
                com.prubsn.bean.LoginBeanSoapBindingStub _stub = new com.prubsn.bean.LoginBeanSoapBindingStub(new java.net.URL(LoginBean_address), this);
                _stub.setPortName(getLoginBeanWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LoginBean".equals(inputPortName)) {
            return getLoginBean();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://bean.prubsn.com", "LoginBeanService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://bean.prubsn.com", "LoginBean"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LoginBean".equals(portName)) {
            setLoginBeanEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
