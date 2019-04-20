/**
 * EASLoginProxyService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeecg.xincheng.ws.easlogin;

public interface EASLoginProxyService extends javax.xml.rpc.Service {
    public java.lang.String getEASLoginAddress();

    public com.jeecg.xincheng.ws.easlogin.EASLoginProxy getEASLogin() throws javax.xml.rpc.ServiceException;

    public com.jeecg.xincheng.ws.easlogin.EASLoginProxy getEASLogin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
