/**
 * WSBaoLiFacade2SrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeecg.xincheng.ws.easbaoli;

public class WSBaoLiFacade2SrvProxyServiceLocator extends org.apache.axis.client.Service implements com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SrvProxyService {

    public WSBaoLiFacade2SrvProxyServiceLocator() {
    }


    public WSBaoLiFacade2SrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSBaoLiFacade2SrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSBaoLiFacade2
    private java.lang.String WSBaoLiFacade2_address = "http://10.0.8.171:6888/ormrpc/services/WSBaoLiFacade2";

    public java.lang.String getWSBaoLiFacade2Address() {
        return WSBaoLiFacade2_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSBaoLiFacade2WSDDServiceName = "WSBaoLiFacade2";

    public java.lang.String getWSBaoLiFacade2WSDDServiceName() {
        return WSBaoLiFacade2WSDDServiceName;
    }

    public void setWSBaoLiFacade2WSDDServiceName(java.lang.String name) {
        WSBaoLiFacade2WSDDServiceName = name;
    }

    public com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SrvProxy getWSBaoLiFacade2() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSBaoLiFacade2_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSBaoLiFacade2(endpoint);
    }

    public com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SrvProxy getWSBaoLiFacade2(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SoapBindingStub _stub = new com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SoapBindingStub(portAddress, this);
            _stub.setPortName(getWSBaoLiFacade2WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSBaoLiFacade2EndpointAddress(java.lang.String address) {
        WSBaoLiFacade2_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SoapBindingStub _stub = new com.jeecg.xincheng.ws.easbaoli.WSBaoLiFacade2SoapBindingStub(new java.net.URL(WSBaoLiFacade2_address), this);
                _stub.setPortName(getWSBaoLiFacade2WSDDServiceName());
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
        if ("WSBaoLiFacade2".equals(inputPortName)) {
            return getWSBaoLiFacade2();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.0.8.171:6888/ormrpc/services/WSBaoLiFacade2", "WSBaoLiFacade2SrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.0.8.171:6888/ormrpc/services/WSBaoLiFacade2", "WSBaoLiFacade2"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSBaoLiFacade2".equals(portName)) {
            setWSBaoLiFacade2EndpointAddress(address);
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
