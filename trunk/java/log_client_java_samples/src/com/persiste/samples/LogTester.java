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



import java.util.HashMap;

import com.persiste.sdk.INotifiable;
import com.persiste.sdk.LogServiceClient;
import com.persiste.sdk.LogserviceResponse;



public class LogTester {
	public static void main(String args[]){
		//initialise service callback passing true will make all service call to be performed asynchronously 
		LogServiceClient.initialise(new INotifiable() {
			@Override
			public void LoggingClientCompleted(LogserviceResponse response, boolean http_error_flag) {
				//check http error and service call status
				System.out.println(response.getStatus());
				System.out.println(response.getMessage());
		
			}
		}, true);
		
		/*save log with custom fields
		HashMap<String, String> custom_field=new HashMap<String, String>();
		custom_field.put("Marine", "laval");
		custom_field.put("Color", "Green");
		LogServiceClient.warn("Java Log", "gjhgjh", "evan@hor.fr", custom_field, "");
		*/
		
        //save log without fields
		//LogServiceClient.warn("Java Log", "gjhgjh", "evan@hor.fr");

        //get logs by page (first page)
        //LogServiceClient.get(1);
        
        //delete log
        //LogServiceClient.delete(38);

		
	}
}
