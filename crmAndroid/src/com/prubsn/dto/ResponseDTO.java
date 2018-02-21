/**
 * ResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.prubsn.dto;

public class ResponseDTO  implements java.io.Serializable {
    private java.lang.String agtCode;

    private java.lang.String agtId;

    private java.lang.String channelCode;

    private java.lang.String CHANNEL_NAME;

    private java.lang.String errorCode;

    private java.lang.String errorMsg;

    private boolean leadearFlg;

    private java.lang.String name;

    private java.lang.String region;

    public ResponseDTO() {
    }

    public ResponseDTO(
           java.lang.String agtCode,
           java.lang.String agtId,
           java.lang.String channelCode,
           java.lang.String CHANNEL_NAME,
           java.lang.String errorCode,
           java.lang.String errorMsg,
           boolean leadearFlg,
           java.lang.String name,
           java.lang.String region) {
           this.agtCode = agtCode;
           this.agtId = agtId;
           this.channelCode = channelCode;
           this.CHANNEL_NAME = CHANNEL_NAME;
           this.errorCode = errorCode;
           this.errorMsg = errorMsg;
           this.leadearFlg = leadearFlg;
           this.name = name;
           this.region = region;
    }


    /**
     * Gets the agtCode value for this ResponseDTO.
     * 
     * @return agtCode
     */
    public java.lang.String getAgtCode() {
        return agtCode;
    }


    /**
     * Sets the agtCode value for this ResponseDTO.
     * 
     * @param agtCode
     */
    public void setAgtCode(java.lang.String agtCode) {
        this.agtCode = agtCode;
    }


    /**
     * Gets the agtId value for this ResponseDTO.
     * 
     * @return agtId
     */
    public java.lang.String getAgtId() {
        return agtId;
    }


    /**
     * Sets the agtId value for this ResponseDTO.
     * 
     * @param agtId
     */
    public void setAgtId(java.lang.String agtId) {
        this.agtId = agtId;
    }


    /**
     * Gets the channelCode value for this ResponseDTO.
     * 
     * @return channelCode
     */
    public java.lang.String getChannelCode() {
        return channelCode;
    }


    /**
     * Sets the channelCode value for this ResponseDTO.
     * 
     * @param channelCode
     */
    public void setChannelCode(java.lang.String channelCode) {
        this.channelCode = channelCode;
    }


    /**
     * Gets the CHANNEL_NAME value for this ResponseDTO.
     * 
     * @return CHANNEL_NAME
     */
    public java.lang.String getCHANNEL_NAME() {
        return CHANNEL_NAME;
    }


    /**
     * Sets the CHANNEL_NAME value for this ResponseDTO.
     * 
     * @param CHANNEL_NAME
     */
    public void setCHANNEL_NAME(java.lang.String CHANNEL_NAME) {
        this.CHANNEL_NAME = CHANNEL_NAME;
    }


    /**
     * Gets the errorCode value for this ResponseDTO.
     * 
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this ResponseDTO.
     * 
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the errorMsg value for this ResponseDTO.
     * 
     * @return errorMsg
     */
    public java.lang.String getErrorMsg() {
        return errorMsg;
    }


    /**
     * Sets the errorMsg value for this ResponseDTO.
     * 
     * @param errorMsg
     */
    public void setErrorMsg(java.lang.String errorMsg) {
        this.errorMsg = errorMsg;
    }


    /**
     * Gets the leadearFlg value for this ResponseDTO.
     * 
     * @return leadearFlg
     */
    public boolean isLeadearFlg() {
        return leadearFlg;
    }


    /**
     * Sets the leadearFlg value for this ResponseDTO.
     * 
     * @param leadearFlg
     */
    public void setLeadearFlg(boolean leadearFlg) {
        this.leadearFlg = leadearFlg;
    }


    /**
     * Gets the name value for this ResponseDTO.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ResponseDTO.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the region value for this ResponseDTO.
     * 
     * @return region
     */
    public java.lang.String getRegion() {
        return region;
    }


    /**
     * Sets the region value for this ResponseDTO.
     * 
     * @param region
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseDTO)) return false;
        ResponseDTO other = (ResponseDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agtCode==null && other.getAgtCode()==null) || 
             (this.agtCode!=null &&
              this.agtCode.equals(other.getAgtCode()))) &&
            ((this.agtId==null && other.getAgtId()==null) || 
             (this.agtId!=null &&
              this.agtId.equals(other.getAgtId()))) &&
            ((this.channelCode==null && other.getChannelCode()==null) || 
             (this.channelCode!=null &&
              this.channelCode.equals(other.getChannelCode()))) &&
            ((this.CHANNEL_NAME==null && other.getCHANNEL_NAME()==null) || 
             (this.CHANNEL_NAME!=null &&
              this.CHANNEL_NAME.equals(other.getCHANNEL_NAME()))) &&
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode()))) &&
            ((this.errorMsg==null && other.getErrorMsg()==null) || 
             (this.errorMsg!=null &&
              this.errorMsg.equals(other.getErrorMsg()))) &&
            this.leadearFlg == other.isLeadearFlg() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.region==null && other.getRegion()==null) || 
             (this.region!=null &&
              this.region.equals(other.getRegion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAgtCode() != null) {
            _hashCode += getAgtCode().hashCode();
        }
        if (getAgtId() != null) {
            _hashCode += getAgtId().hashCode();
        }
        if (getChannelCode() != null) {
            _hashCode += getChannelCode().hashCode();
        }
        if (getCHANNEL_NAME() != null) {
            _hashCode += getCHANNEL_NAME().hashCode();
        }
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getErrorMsg() != null) {
            _hashCode += getErrorMsg().hashCode();
        }
        _hashCode += (isLeadearFlg() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.prubsn.com", "ResponseDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agtCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "agtCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agtId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "agtId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "channelCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CHANNEL_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "CHANNEL_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "errorMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadearFlg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "leadearFlg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.prubsn.com", "region"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
