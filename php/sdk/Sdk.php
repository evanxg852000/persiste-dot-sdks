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

/*
 * The log server url (do not change this )
 */
define("LOG_SERVICE_ENDPOINT", "http://flix.dev/services/native/logs/") ;

/*
 * The application unic id
 */
define("LOG_SERVICE_APP_UNICID", "4F59CB0433Z1C221869268") ;

/*
 * Your api key
 */
define("LOG_SERVICE_API_KEY", "4F59695A496E4853357468") ;

/*
* Your api secret
*/
define("LOG_SERVICE_API_SECRET", "-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWx44LOUDFY34hAALwMl1cv576
nRzMKdPflXb78pLMexHHxMNJhS766IOpAgLoIxxewXdmOL8I6/qZFK3SsGQ6GPRM
beYARyzGumNBOFJVwOYTpwEjydUVgMvRTq3gH5oNHrl48Y0kN+u4J9DxY0OkiGlj
aytv+5CvPfhGxZ4+DQIDAQAB
-----END PUBLIC KEY-----");

/*
 * The local file in which log are saved when cURL is not available
 */
define("LOG_SERVICE_FILE", "log.txt") ;

require_once 'Core.php';
require_once 'HttpClient.php';
require_once 'LogServiceClient.php';

/*	examples
tell  http://flix.dev/services/native/logs/tell/4F59CB0433Z1C221869268/4F59695A496E4853357468/
put   http://flix.dev/services/native/logs/put/4F59CB0433Z1C221869268/4F59695A496E4853357468
get   http://flix.dev/services/native/logs/get/4F59CB0433Z1C221869268/4F59695A496E4853357468/1
delete http://flix.dev/services/native/logs/delete/4F59CB0433Z1C221869268/4F59695A496E4853357468/2
*/