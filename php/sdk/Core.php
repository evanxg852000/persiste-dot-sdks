<?php 

/**
 * 
 * Log level
 * @author Evansofts
 *
 */
class LogLevel{
	const ERROR=0;
	const WARNING=1;
	const INFO=2;
	const SUCCESS=3;
}

class Log {
    public $level;
    public $title;
    public $description;
    public $emiter;
    public $custom_fields;
    public $stack_trace;
    
    
    public function __construct($level, $title, $description, $emiter="",$custom_fields=array(), $stack_trace=""){
    	$this->level=$level;
        $this->title=$title;
        $this->description=$description;
        $this->stack_trace=$stack_trace;
        $this->custom_fields=$custom_fields;
        $this->emiter=$emiter;
    }

}


abstract class CallableMethod{
	public abstract function call();
	
	protected function isFailure(&$response){
		if($response=="404"){
			$response=json_encode(array('status'=>'failure','message'=>'Missing required parameters'));
			return true;
		}
		
		if($response=="401"){
			$response=json_encode(array('status'=>'failure','message'=>"Authentication error, check your credentials"));
			return true;
		}
		
	}
	
	protected function packData($data){
		$random_salt=Symencrypter::UnicID(LOG_SERVICE_APP_UNICID);
		$sym_encrypter=new Symencrypter($random_salt);
		$data=$sym_encrypter->Encrypt(json_encode($data));

		$openssl_encrypter=new ClientEncrypter(LOG_SERVICE_API_SECRET);
		$pass_phrase=$openssl_encrypter->Encrypt($random_salt);
		
		$data=json_encode(array('passphrase'=>base64_encode($pass_phrase),'body'=>$data));
		return $data;
	}
	
	protected function unpackData($response){
		$response=json_decode($response);
		
		$openssl_decrypter=new ClientEncrypter(LOG_SERVICE_API_SECRET);
		$pass_phrase=$openssl_decrypter->Decrypt(base64_decode($response->passphrase));
		
		$asym_decrypter=new Symencrypter($pass_phrase);
		return $asym_decrypter->Decrypt($response->body);
	}
	
}


/**
 * 
 * This class wraps the put Method
 * @author Evansofts
 *
 */
class  Put extends CallableMethod{
	protected $_log;

	public function __construct($log){
            $this->_log=$log;
	}

	/**
	 * //json {status:[OK|BAD],id:23}
	 * @return String | HttpResponse 
	 */
	public function call(){
		$data=$this->packData($this->_log);
				
		//curl not available ? -call tell method 
		if(!HttpClient::isEnabled()){ 
			//save log in file
			if(!file_put_contents(LOG_SERVICE_FILE, $data)){
				return json_encode(array('status'=>'error','message'=>'Unable to write log in the local log file.'));
			}
			
			//notify the server to grab the log file
			$response=file_get_contents(LOG_SERVICE_ENDPOINT."tell/".LOG_SERVICE_APP_UNICID."/".LOG_SERVICE_API_KEY); 
			
			//empty log file to prevent exposure
			file_put_contents(LOG_SERVICE_FILE, "");
			
			if($this->isFailure($response)){
				return $response;
			}
			//return the decrypted response 
			return $this->unpackData($response);
		}
            
		//curl available 
		$http=new HttpClient(LOG_SERVICE_ENDPOINT."put/".LOG_SERVICE_APP_UNICID."/".LOG_SERVICE_API_KEY);
		$http->post(array('content'=>$data));
		if($http->getLastResponse()->getStatus()==HttpResponse::SUCCESS){
			$response=$http->getLastResponse()->getContent();
			
			if($this->isFailure($response)){
				return $response;
			}
			
			//return the decrypted response 
			return $this->unpackData($response);
		}
		$error=$http->getLastResponse()->getErrorMessage();
		return json_encode(array('status'=>'error','message'=>$error));
	}

}



/**
 * This class wrap the get method 
 */
class  Get extends CallableMethod{
	protected $_page;

	public function __construct($page=1){
		$this->_page=$page;
	}

	/**
	 *
	 * @return string {status:[SUCCESS|FAILURE|ERROR],data:{...list...}}
	 */
	public function call(){
		if(!HttpClient::isEnabled()){
        	$response=file_get_contents(LOG_SERVICE_ENDPOINT."get/".LOG_SERVICE_APP_UNICID."/".LOG_SERVICE_API_KEY."/".$this->_page); 
			
			if($this->isFailure($response)){
	        	return $response;
	        }
	        //return the decrypted response 
			return $this->unpackData($response);
		 }
        
		 //curl available
		 $http=new HttpClient(LOG_SERVICE_ENDPOINT."get/".LOG_SERVICE_APP_UNICID."/".LOG_SERVICE_API_KEY."/".$this->_page);
		 $http->get();
		 if($http->getLastResponse()->getStatus()==HttpResponse::SUCCESS){
		 	$response=$http->getLastResponse()->getContent();
		 		
		 	if($this->isFailure($response)){
		 		return $response;
		 	}
		 		
		 	//return the decrypted response
		 	return $this->unpackData($response);
		 }
		 $error=$http->getLastResponse()->getErrorMessage();
		 return json_encode(array('status'=>'error','message'=>$error));
       
	}

}

/**
 * This call wraps the delete method
 * @author Evansofts
 *
 */
class  Delete extends CallableMethod{
	protected $_id;

	public function __construct($id){
		$this->_id=$id;
	}

	/**
	 *
	 * @return string {status:[OK|BAD]}
	 */
	public function call(){
		if(!HttpClient::isEnabled()){
			$response=file_get_contents(LOG_SERVICE_ENDPOINT."delete/".LOG_SERVICE_APP_UNICID."/".LOG_SERVICE_API_KEY."/".$this->_id); 
			
			if($this->isFailure($response)){
				return $response;
			}
			//return the decrypted response
			return $this->unpackData($response);
		}
        
		//curl available
		$http=new HttpClient(LOG_SERVICE_ENDPOINT."delete/".LOG_SERVICE_APP_UNICID."/".LOG_SERVICE_API_KEY."/".$this->_id);
		$http->get();
		if($http->getLastResponse()->getStatus()==HttpResponse::SUCCESS){
			$response=$http->getLastResponse()->getContent();
			 
			if($this->isFailure($response)){
				return $response;
			}
			 
			//return the decrypted response
			return $this->unpackData($response);
		}
		$error=$http->getLastResponse()->getErrorMessage();
		return json_encode(array('status'=>'error','message'=>$error));

	}

}

/**
 *
 * data encryption class with api_secret
 * @author Evansofts
 *
 */
class ClientEncrypter{
	protected $_public_key ;

	public function __construct($api_secret){
		$this->_public_key=$api_secret;
	}

	public function Encrypt($data){
		$encrypted_data=null;
		if(!openssl_public_encrypt($data,$encrypted_data, $this->_public_key)) {
			return false;
		}
		return $encrypted_data;
	}

	public function Decrypt($encrypted_data){
		$decrypted_data=null;
		if(!openssl_public_decrypt($encrypted_data, $decrypted_data, $this->_public_key)){
			return false;
		}
		return $decrypted_data;
	}

}


class Symencrypter {
	const  DES        = MCRYPT_DES;
	const  BLOWFISH   = MCRYPT_BLOWFISH;
	const  GOST       = MCRYPT_GOST;
	const  RC2        = MCRYPT_RC2;


	private $method;
	private $salt;

	public function  __construct($salt, $method=Symencrypter::BLOWFISH ) {
		$this->salt=substr($salt, 0, mcrypt_get_key_size($method,MCRYPT_MODE_ECB));
		$this->method=$method;
	}

	public function Encrypt($data){
		$encrypted= mcrypt_encrypt($this->method, $this->salt, $data, MCRYPT_MODE_ECB);
		return trim(base64_encode($encrypted));
	}

	public function Decrypt($data){
		return trim(mcrypt_decrypt($this->method, $this->salt,base64_decode($data),MCRYPT_MODE_ECB));
	}
	
	public static function UnicID($prefix=''){
		return str_replace(".",'',uniqid($prefix, true));
	}
	
}