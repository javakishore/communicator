package com.company.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Zone {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_ZONE_SEQ")
	@SequenceGenerator(name="TAB_ZONE_SEQ", sequenceName="TAB_ZONE_SEQ", allocationSize=1)
	public Integer zoneId;
	public String zoneName;
	
	public Zone() {
		// TODO Auto-generated constructor stub
	}
	public Zone(Integer zoneId,String zoneName) {
		// TODO Auto-generated constructor stub
		this.zoneId=zoneId;
		this.zoneName=zoneName;
	}
	
	public Integer getZoneId()
	{
		return this.zoneId;
	}
	
	public String getZoneName()
	{
		return this.zoneName;
	}
	
	public void setZoneId(Integer zoneId)
	{
		this.zoneId=zoneId;
	}
	
	public void setZoneName(String zoneName)
	{
		this.zoneName=zoneName;
	}
}
