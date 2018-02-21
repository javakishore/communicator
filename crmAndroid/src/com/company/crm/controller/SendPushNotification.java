package com.company.crm.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class SendPushNotification {

	@RequestMapping(value="/sendPushNotification", method = RequestMethod.POST)
	public void SendNotification()
	{
		//Setup the connection

		ApnsService service =
		    APNS.newService()
		    .withCert("D:/Varsha/apache-tomcat-8.0.24/webapps/ROOT/Apple/sslcertkey.p12", "jktls1234")
		    .withSandboxDestination()
		    .build();
		
		//Create and send the message

		String payload = APNS.newPayload().alertBody("Can't be simpler than this!").build();
		String token = "4749250256b6015ddb2ff3241d7059b3fd1aa269c2b780c68b3e41728e3bdca5";
		service.push(token, payload);

		Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for (String deviceToken : inactiveDevices.keySet()) {
		    Date inactiveAsOf = inactiveDevices.get(deviceToken);
		    		}
	}
}
