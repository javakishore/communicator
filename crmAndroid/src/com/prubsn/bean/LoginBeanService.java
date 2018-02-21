/**
 * LoginBeanService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.prubsn.bean;

public interface LoginBeanService extends javax.xml.rpc.Service {
    public java.lang.String getLoginBeanAddress();

    public com.prubsn.bean.LoginBean getLoginBean() throws javax.xml.rpc.ServiceException;

    public com.prubsn.bean.LoginBean getLoginBean(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
