package com.company.crm.controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SendPushNotification {

	@RequestMapping(value="/sendPushNotification", method = RequestMethod.POST)
	public void SendNotification()
	{
		//server side code for onesignal
		
		try {
			   String jsonResponse;
			   
			   URL url = new URL("https://onesignal.com/api/v1/notifications");
			   HttpURLConnection con = (HttpURLConnection)url.openConnection();
			   con.setUseCaches(false);
			   con.setDoOutput(true);
			   con.setDoInput(true);

			   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			   con.setRequestProperty("Authorization", "Basic YWY1ODQzYzktM2M0Ny00N2Q0LWFlYjktMDUwZjdlZTFjMzcx");
			   con.setRequestMethod("POST");

			   String strJsonBody = "{"
			                      +   "\"app_id\": \"2d3c270a-ebbc-4731-a7ab-7ce08c473cbe\","
			                      +   "\"included_segments\": [\"All\"],"
			                      +   "\"data\": {\"foo\": \"bar\"},"
			                      +   "\"contents\": {\"en\": \"GO TO MESSAGES SATYA\"}"
			                      + "}";
			         
			   
			   System.out.println("xxxxxxxxxx strJsonBody:\n" + strJsonBody);

			   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			   con.setFixedLengthStreamingMode(sendBytes.length);

			   OutputStream outputStream = con.getOutputStream();
			   outputStream.write(sendBytes);

			   int httpResponse = con.getResponseCode();
			   System.out.println("httpResponse: " + httpResponse);

			   if (  httpResponse >= HttpURLConnection.HTTP_OK
			      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
			      //Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			      //jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      jsonResponse = "hello 1111111 NOT SUCCESS";
			      
			      //scanner.close();
			   }
			   else {
			      //Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			      //jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      //scanner.close();
			      jsonResponse = "hello 2222222 SUCCESS";
			   }
			   System.out.println(" XXXXXXXXXXXXXX jsonResponse:\n" + jsonResponse);
			   
			} catch(Throwable t) {
			   t.printStackTrace();
			}
		
		/*
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
		    		}*/
	}
}
