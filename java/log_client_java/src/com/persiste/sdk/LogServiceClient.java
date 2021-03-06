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

import java.util.HashMap;
import java.util.Map;



public class LogServiceClient {
	private CallableMethod method; 
	
	public static void initialise(INotifiable  notification_receiver,boolean is_async){
		HttpClientWrapper.setParams(notification_receiver, is_async);
	}
	
	public LogServiceClient(CallableMethod method) {
		this.method=method;	
	}
	
	public  void run(){
		this.method.call();
	}
	
	public static void setParams(INotifiable notification_receiver,boolean is_async){
		HttpClientWrapper.setParams(notification_receiver,is_async);
	}

	public static void error(String title,String description,String emiter,Map<String, String> custom_field,String stack_trace){
		LogServiceClient.log(LogLevel.ERROR, title, description, emiter, custom_field, stack_trace);
	}
	
	public static void error(String title,String description,String emiter,Map<String, String> custom_field){
		LogServiceClient.log(LogLevel.ERROR, title, description, emiter, custom_field, "");
	}
	
	public static void error(String title,String description,String emiter){
		LogServiceClient.log(LogLevel.ERROR, title, description, emiter, new HashMap<String, String>(), "");
	}
	
	public static void error(String title,String description){
		LogServiceClient.log(LogLevel.ERROR, title, description, "", new HashMap<String, String>(), "");
	}
	
	
	public static void warn(String title,String description,String emiter,Map<String, String> custom_field,String stack_trace){
		LogServiceClient.log(LogLevel.WARNING, title, description, emiter, custom_field, stack_trace);
	}
	
	public static void warn(String title,String description,String emiter,Map<String, String> custom_field){
		LogServiceClient.log(LogLevel.WARNING, title, description, emiter, custom_field, "");
	}
	
	public static void warn(String title,String description,String emiter){
		LogServiceClient.log(LogLevel.WARNING, title, description, emiter, new HashMap<String, String>(), "");
	}
	
	public static void warn(String title,String description){
		LogServiceClient.log(LogLevel.WARNING, title, description, "", new HashMap<String, String>(), "");
	}
	
	
	public static void info(String title,String description,String emiter,Map<String, String> custom_field,String stack_trace){
		LogServiceClient.log(LogLevel.INFO, title, description, emiter, custom_field, stack_trace);
	}
	
	public static void info(String title,String description,String emiter,Map<String, String> custom_field){
		LogServiceClient.log(LogLevel.INFO, title, description, emiter, custom_field, "");
	}
	
	public static void info(String title,String description,String emiter){
		LogServiceClient.log(LogLevel.INFO, title, description, emiter, new HashMap<String, String>(), "");
	}
	
	public static void info(String title,String description){
		LogServiceClient.log(LogLevel.INFO, title, description, "", new HashMap<String, String>(), "");
	}
	
	
	public static void success(String title,String description,String emiter,Map<String, String> custom_field,String stack_trace){
		LogServiceClient.log(LogLevel.SUCCESS, title, description, emiter, custom_field, stack_trace);
	}
	
	public static void success(String title,String description,String emiter,Map<String, String> custom_field){
		LogServiceClient.log(LogLevel.SUCCESS, title, description, emiter, custom_field, "");
	}
	
	public static void success(String title,String description,String emiter){
		LogServiceClient.log(LogLevel.SUCCESS, title, description, emiter, new HashMap<String, String>(), "");
	}
	
	public static void success(String title,String description){
		LogServiceClient.log(LogLevel.SUCCESS, title, description, "", new HashMap<String, String>(), "");
	}
	
	
	private static void log(int level,String title,String description,String emiter,Map<String, String> custom_field,String stack_trace){
		Log log=new Log(level,title,description,emiter,custom_field,stack_trace);
		LogServiceClient client=new LogServiceClient(new Put(log));
		client.run();
	}
	
	
	public static void get(){
		LogServiceClient client=new LogServiceClient(new Get());
		client.run();
	}
	
	public static void get(int page){
		LogServiceClient client=new LogServiceClient(new Get(page));
		client.run();
	}
	
	public static void delete(int id){
		LogServiceClient client=new LogServiceClient(new Delete(id));
		client.run();
	}
	  

}
