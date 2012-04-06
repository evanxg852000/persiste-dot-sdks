/**
* Persiste. Java SDK
*
* The cloud based logging platform.
*
* @package             Persiste. SDK
* @author              Evance Soumaoro
* @copyright           Copyright (c) 2012 - 20xx, Evansofts.
* @license             Evansofts Proprietary Licence (EPL)
* @link                http://evansofts.com
* @version             Version 0.1
*/

package com.persiste.samples;



import java.util.ArrayList;
import java.util.HashMap;


import com.persiste.sdk.INotifiable;
import com.persiste.sdk.LogServiceClient;
import com.persiste.sdk.LogserviceResponse;
import com.persiste.sdk.Sdk;



public class LogTester {
	public static void main(String args[]){
		//configure
		Sdk.LOG_SERVICE_APP_UNICID="4F59CB0433Z1C221869268" ; 
		Sdk.LOG_SERVICE_API_KEY="4F59695A496E4853357468" ;
		Sdk.LOG_SERVICE_API_SECRET="not in use in this sdk";
		
		//initialise service callback passing true will make all service call to be performed asynchronously 
		LogServiceClient.initialise(new INotifiable() {
			@Override
			public void LoggingClientCompleted(LogserviceResponse response, boolean http_error_flag) {
				//check http error and service call status
				if(response!=null){
					System.out.println(response.getStatus());
					System.out.println(response.getMessage());
					
					ArrayList<Object> logs=response.getData();
				}
				
		
			}
		}, true);
		
		//save log with custom fields
		HashMap<String, String> custom_field=new HashMap<String, String>();
		custom_field.put("Marine", "laval");
		custom_field.put("Color", "Green");
		//LogServiceClient.warn("Java Log", "gjhgjh", "evan@hor.fr", custom_field, "");
	
		
        //save log without fields
		LogServiceClient.warn("Java Log", "gjhgjh", "evan@hor.fr");

        //get logs by page (first page)
        LogServiceClient.get(1);
        
        //delete log
        LogServiceClient.delete(38);

		
	}
}
