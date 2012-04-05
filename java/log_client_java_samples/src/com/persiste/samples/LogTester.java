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



import com.persiste.sdk.INotifiable;
import com.persiste.sdk.LogServiceClient;
import com.persiste.sdk.LogserviceResponse;



public class LogTester {
	public static void main(String args[]){
		LogServiceClient.initialise(new INotifiable() {
			@Override
			public void LoggingClientCompleted(LogserviceResponse response, boolean http_error_flag) {
				//check http error and service call status
				System.out.println(response.getStatus());
				System.out.println(response.getMessage());
		
			}
		}, true);
		
		LogServiceClient.get();
	}
}
