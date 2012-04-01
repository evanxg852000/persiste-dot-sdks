<?php
class LogServiceClient {
	private $_method; 

	public function __construct(CallableMethod $method){
		$this->_method=$method;	
	}
	
	public  function run(){
		return $this->_method->call();
	}
	

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
	
	
	public static function get($page=1){
		$client=new LogServiceClient(new Get($page));
		return $client->run();
	}
	
	public static function delete($id){
		 $client=new LogServiceClient(new Delete($id));
		 return $client->run();
	}
		
}
