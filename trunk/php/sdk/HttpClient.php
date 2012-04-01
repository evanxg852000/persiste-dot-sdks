<?php
class HttpResponse{
	const NOT_AVAILABLE=-1;
	const ERROR=0;
	const SUCCESS=1;
	
	protected $_status;
	protected $_content;  			 // http response
	protected $_info; 
	protected $_error_code;          // Error code returned as an int
	protected $_error_msg;           // Error message returned as a string
	
	function __construct($status, $content, $info, $error_code=NULL, $error_msg=NULL){
		$this->_status=$status;
		$this->_content=$content;
		$this->_info=$info;
		$this->_error_code=$error_code;
		$this->_error_msg=$error_msg;
	}
	
	public function getStatus(){
		return $this->_status;
	}

	public function getContent(){
		return $this->_content;
	}
	
	public function getInfo(){
		return $this->_info;
	}

	public function getErrorCode(){
		return $this->_error_code;
	}
	
	public function getErrorMessage(){
		return $this->_error_msg;
	}
	
}

class HttpOption{
	
	
}

class HttpMethod{
	const PUT="PUT";
	const GET="GET";
	const POST="POST";
	const DELETE="DELETE";	
} 

class HttpClient {
	
	protected $_url;                 // url of the session
	protected $_session;             // cURL handle
	protected $_options = array();   // cURL option array
	protected $_headers = array();   // extra http headers
	protected $_response;     		 // http response
	protected $_last_response;     	 // http response
	
	public function __construct($url){
		$this->_response=NULL;
		if ( ! HttpClient::isEnabled()){
			$this->_response=new HttpResponse(HttpResponse::NOT_AVAILABLE, false, "cURL is not available");
			return;
		}
		$this->_last_response=$this->_response;
		$this->reset();
		
		$this->_url =$url;
		$this->_session = curl_init($this->_url);
	}
	
	public static function isEnabled(){
		return function_exists('curl_init');
	}
	
	public function reset(){
		$this->_session = NULL;
		$this->_options = array();
		$this->_headers = array();
		$this->_response = NULL;
		return $this;
	}
	
	public function addOption($code, $value){
		if (is_string($code) && !is_numeric($code))	{
			$code = constant('CURLOPT_' . strtoupper($code));
		}
	
		$this->_options[$code] = $value;
		return $this;
	}

	public function addOptions($options = array()){
		foreach ($options as $option_code => $option_value)	{
			$this->addOption($option_code, $option_value);
		}
	
		// set all options provided
		curl_setopt_array($this->_session, $this->_options);
	
		return $this;
	}
	
	public function setCookies($data = array()){
		if (is_array($data))
		{
			$data = http_build_query($data, NULL, '&');
		}
	
		$this->addOption(CURLOPT_COOKIE, $data);
		return $this;
	}
	
	public function  getLastResponse(){
		return $this->_last_response;
	}
		
	
	
	public function get($data=array(), $options = array()){
		$query_string=(is_array($data))? http_build_query($data, NULL, '&'): '';
		$this->_session = curl_init($this->_url.$query_string);
			
		//add options
		$this->addOptions($options);
		
		//execute
		return $this->execute();
	} 
		
	public function post($data, $options = array()){
		// is array ?
		if (is_array($data)){
			$data = http_build_query($data, NULL, '&');
		}
		
		//add options
		$this->addOptions($options);
		
		$this->setMethod(HttpMethod::POST);
		
		$this->addOption(CURLOPT_POST, TRUE);
		$this->addOption(CURLOPT_POSTFIELDS, $data);
		
		//execute
		return $this->execute();
	}
	
	public function put($data = array(), $options = array()){
		// is array ?
		if (is_array($data)){
			$data = http_build_query($data, NULL, '&');
		}
	
		//add options
		$this->addOptions($options);
	
		$this->setMethod(HttpMethod::PUT);
		
		$this->addOption(CURLOPT_POSTFIELDS, $data);
	
		// Override method, I think this overrides $_POST with PUT data but... we'll see eh?
		//$this->option(CURLOPT_HTTPHEADER, array('X-HTTP-Method-Override: PUT'));
		
		//execute
		return $this->execute();
	}

	public function delete($data, $options = array()){
		// is array ?
		if (is_array($data)){
			$data = http_build_query($data, NULL, '&');
		}
	
		//add options
		$this->addOptions($options);
	
		$this->setMethod(HttpMethod::DELETE);
	
		$this->addOption(CURLOPT_POSTFIELDS, $data);
		
		//execute
		return $this->execute();
	}

	private function setMethod($method){
		$this->_options[CURLOPT_CUSTOMREQUEST] = strtoupper($method);
	}
	
	private  function execute(){
		//set some default params  
		if ( ! isset($this->_options[CURLOPT_TIMEOUT])){
			$this->_options[CURLOPT_TIMEOUT] = 30;
		}
		
		if ( ! isset($this->_options[CURLOPT_RETURNTRANSFER])){
			$this->_options[CURLOPT_RETURNTRANSFER] = TRUE;
		}
		
		if ( ! isset($this->_options[CURLOPT_FAILONERROR])){
			$this->_options[CURLOPT_FAILONERROR] = TRUE;
		}
	
		// set follow location Only if we are not running securely
		if ( ! ini_get('safe_mode') && ! ini_get('open_basedir')){
			//is it already set ?
			if ( ! isset($this->options[CURLOPT_FOLLOWLOCATION])){
				$this->options[CURLOPT_FOLLOWLOCATION] = TRUE;
			}
		}
	
		if ( ! empty($this->_headers)){
			$this->addOption(CURLOPT_HTTPHEADER, $this->_headers);
		}
		
		//update options
		$this->addOptions();
	
		// execute the request & and hide all output
		$response = curl_exec($this->_session);
		$info = curl_getinfo($this->_session);
		
	
		
		// is request successful ?
		if($response !== FALSE){
			curl_close($this->_session);
			$this->reset();
			
			$this->_response=new HttpResponse(HttpResponse::SUCCESS, $response, $info);
			$this->_last_response=$this->_response;			
			return $this;
		}
	
		//request failed 
		$error_code = curl_errno($this->_session);
		$error_msg = curl_error($this->_session);
		curl_close($this->_session);
		$this->reset();
		
		$this->_response=new HttpResponse(HttpResponse::ERROR, FALSE, $info, $error_code, $error_msg);
		$this->_last_response=$this->_response;
		return $this;
		
	}
	

}