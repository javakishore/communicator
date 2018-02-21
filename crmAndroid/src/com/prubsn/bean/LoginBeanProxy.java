package com.prubsn.bean;

public class LoginBeanProxy implements com.prubsn.bean.LoginBean {
  private String _endpoint = null;
  private com.prubsn.bean.LoginBean loginBean = null;
  
  public LoginBeanProxy() {
    _initLoginBeanProxy();
  }
  
  public LoginBeanProxy(String endpoint) {
    _endpoint = endpoint;
    _initLoginBeanProxy();
  }
  
  private void _initLoginBeanProxy() {
    try {
      loginBean = (new com.prubsn.bean.LoginBeanServiceLocator()).getLoginBean();
      if (loginBean != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loginBean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loginBean)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loginBean != null)
      ((javax.xml.rpc.Stub)loginBean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.prubsn.bean.LoginBean getLoginBean() {
    if (loginBean == null)
      _initLoginBeanProxy();
    return loginBean;
  }
  
  public com.prubsn.dto.ResponseDTO authenticateUser(com.prubsn.dto.RequestDTO reqDTO) throws java.rmi.RemoteException{
    if (loginBean == null)
      _initLoginBeanProxy();
    return loginBean.authenticateUser(reqDTO);
  }
  
  
}