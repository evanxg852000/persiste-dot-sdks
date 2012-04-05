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

package com.persiste.sdk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.persiste.sdk.json.JSONObject;
import com.persiste.sdk.json.JSONValue;


public class HttpClientWrapper {
	private static INotifiable notification_receiver;
	private static boolean is_async;
	
	private String response;
	private String url;
	private boolean has_error;
	
	public HttpClientWrapper(String url) {
		this.response="";
		this.url=url;
		this.has_error=false;
	}
	
	public void get() {
		HttpGetAction action=new HttpGetAction(this.url);
		if(HttpClientWrapper.is_async==true){
			Thread thread = new Thread(action);
			thread.start();
		}else{
			action.run();
		}
	} 
		
	public void post( ArrayList<NameValuePair> data) {
		HttpPostAction action=new HttpPostAction(this.url, data);
		if(HttpClientWrapper.is_async==true){
			Thread thread = new Thread(action);
			thread.start();
		}else{
			action.run();
		}
	} 
	
	public boolean hasError(){
		return this.has_error;		
	}
		
	protected JSONObject packData(HashMap<String, Object> data){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected LogserviceResponse unpackData(String response){
		LogserviceResponse formated_response=null;
		if(response.equals("404")){
			formated_response=new LogserviceResponse("failure","Missing required parameter");
			return formated_response;
		}
		
		if(response.equals("401")){
			formated_response=new LogserviceResponse("failure","Authentication error, check your credentials");
			return formated_response;
		}
		
		try{
			JSONObject json=(JSONObject)JSONValue.parse(response);
			formated_response=new LogserviceResponse((String)json.get("status"),(String)json.get("message"));
			ArrayList<JSONObject> logs=(ArrayList<JSONObject>)json.get("data");
			if(logs!=null){
				ArrayList<Object> data=new ArrayList<Object>();
				Log log=null;
				for(JSONObject obj:logs){
					log=new Log(Integer.parseInt((String)obj.get("level")),(String)obj.get("title"),(String) obj.get("description"), (String )obj.get("emiter_email"), (Map<String, String>) obj.get("custom_fields") , (String) obj.get("stack_trace"));
					data.add(log);
				}
				formated_response.setData(data);
			}	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return formated_response;
	}
	
	public static void setParams(INotifiable  notification_receiver,boolean is_async){
		HttpClientWrapper.notification_receiver=notification_receiver;
		HttpClientWrapper.is_async=is_async;
	}
		
	private class HttpGetAction implements Runnable{
		private String url;
	
		public HttpGetAction(String url) {
			this.url=url;
		}
		
		@Override
		public void run() {
			try {
			    DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpclient = new HttpGet(this.url);
		        HttpResponse http_response = client.execute(httpclient);
		        InputStream content = http_response.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					HttpClientWrapper.this.response += s;
				}
		    } catch (Exception e) { 
		    	HttpClientWrapper.this.response="{\"status\":\"failure\",\"message\":\"Http client error check connection\"}";
		    	HttpClientWrapper.this.has_error=true;
		    }

			if(HttpClientWrapper.notification_receiver!=null){
				LogserviceResponse formated_response=HttpClientWrapper.this.unpackData(HttpClientWrapper.this.response);
				HttpClientWrapper.notification_receiver.LoggingClientCompleted(formated_response,HttpClientWrapper.this.has_error);
			}
			
			
		}
		
	}
		
	private class HttpPostAction implements Runnable{
		private String url;
		private ArrayList<NameValuePair> data;

		public HttpPostAction(String url,ArrayList<NameValuePair> data) {
			this.url=url;
			this.data=data;
		}
		
		@Override
		public void run() {
			try {
			    	HttpClient httpclient = new DefaultHttpClient();
				    HttpPost httppost = new HttpPost(this.url);
			        httppost.setEntity(new UrlEncodedFormEntity(this.data));
			        HttpResponse http_response = httpclient.execute(httppost);
			        InputStream content = http_response.getEntity().getContent();
					BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
					String s = "";
				while ((s = buffer.readLine()) != null) {
					HttpClientWrapper.this.response += s;
				}
			} catch (Exception e) {  
				HttpClientWrapper.this.response="{\"status\":\"failure\",\"message\":\"Http client error check connection\"}";
				HttpClientWrapper.this.has_error=true;
			}

			if(HttpClientWrapper.notification_receiver!=null){
				LogserviceResponse formated_response=HttpClientWrapper.this.unpackData(HttpClientWrapper.this.response);
				HttpClientWrapper.notification_receiver.LoggingClientCompleted(formated_response,HttpClientWrapper.this.has_error);
			}
		}

	}
	
}
