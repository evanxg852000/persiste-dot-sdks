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



public class LogServiceClient {
	private CallableMethod method; 
	
	public LogServiceClient(CallableMethod method) {
		this.method=method;	
	}
	
	public  void run(){
		this.method.call();
	}
	
	public static void setParams(INotifiable notification_receiver,boolean is_async){
		HttpClientWrapper.setParams(notification_receiver,is_async);
	}
	
	/*TODO
	 
	public static function error($title, $description, $emiter="",$custom_fields=array(),$stack_trace=""){
		$log=new Log(LogLevel::ERROR, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	public static function warn($title, $description, $emiter="",$custom_fields=array() ,$stack_trace=""){
		$log=new Log(LogLevel::WARNING, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	public static function info($title, $description, $emiter="",$custom_fields=array() ,$stack_trace=""){
		$log=new Log(LogLevel::INFO, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	public static function success($title, $description, $emiter="",$custom_fields=array() ,$stack_trace=""){
		$log=new Log(LogLevel::SUCCESS, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	 */
	
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
