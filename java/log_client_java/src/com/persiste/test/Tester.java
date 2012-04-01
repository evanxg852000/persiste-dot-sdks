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

package com.persiste.test;


import com.persiste.sdk.HttpClientWrapper;
import com.persiste.sdk.INotifiable;
import com.persiste.sdk.LogServiceClient;
import com.persiste.sdk.json.JSONObject;
import com.persiste.sdk.json.JSONValue;
import com.persiste.sdk.json.parser.JSONParser;

public class Tester {
	public static void main(String args[]){
		HttpClientWrapper.setParams(new INotifiable() {
			@Override
			public void LoggingClientCompleted(JSONObject response, boolean http_error_flag) {
				System.out.println(response);
			}
		}, true);
		
		new HttpClientWrapper("http://flix.dev/services/ntive/logs/get/4F59CB0433Z1C221869268/4F59695A496E4853357468/1").get();
		
		System.err.println("tgdghdh");
	}
}
