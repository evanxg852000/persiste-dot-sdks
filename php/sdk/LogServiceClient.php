<?php
/**
* Persiste. PHP SDK (native)
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

class LogServiceClient {
	private $_method; 

	public function __construct(CallableMethod $method){
		$this->_method=$method;	
	}
	
	public  function run(){
		$json=$this->_method->call();
		$json=json_decode($json);
		$response=new LogserviceResponse($json->status, $json->message);
		if(isset($json->data))
			$response->data=$json->data;
		
		return $response;
	}
	

	public static function error($title, $description, $emiter="",$custom_fields=null,$stack_trace=""){
		$log=new Log(LogLevel::ERROR, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	public static function warn($title, $description, $emiter="",$custom_fields=null ,$stack_trace=""){
		$log=new Log(LogLevel::WARNING, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	public static function info($title, $description, $emiter="",$custom_fields=null ,$stack_trace=""){
		$log=new Log(LogLevel::INFO, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	public static function success($title, $description, $emiter="",$custom_fields=null ,$stack_trace=""){
		$log=new Log(LogLevel::SUCCESS, $title, $description, $emiter, $custom_fields, $stack_trace);
		$client=new LogServiceClient(new Put($log));
		return $client->run();
	}
	
	
	public static function get($page=1){
		$client=new LogServiceClient(new Get($page));
		return $client->run();
	}
	
	public static function delete($id){
		 $client=new LogServiceClient(new Delete($id));
		 return $client->run();
	}
		
}


class LogserviceResponse {
	public $status;
	public $message;
	public $data;

	public function __construct($status, $message,$data=null) {
		$this->status=$status;
		$this->message=$message;
	}
	
}