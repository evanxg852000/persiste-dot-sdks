# Getting the SDK #

First of all download the php sdk from [here](http://persiste-dot-sdks.googlecode.com/files/php_v0.1.zip) , unpack the zip file.

# Integration #

Once the sdk package has been unpacked , drop the sdk folder into your project and include the sdk into your php project using this snippet.
```
<?php
 include("PATH_TO_SDK_FOLDER/Sdk.php");
```

Now open the file you just included `Sdk.php` and configure your application credentials.
Log in your persiste. account , switch to the application and go to account.


![https://lh3.googleusercontent.com/-HlQo1lZMxQk/T33brSMkyCI/AAAAAAAAAnc/tIr0ZgyP6Pg/w703-h527-k/config.png](https://lh3.googleusercontent.com/-HlQo1lZMxQk/T33brSMkyCI/AAAAAAAAAnc/tIr0ZgyP6Pg/w703-h527-k/config.png)

  * Change the constant LOG\_SERVICE\_APP\_UNICID value to reflect the application APP IDENTITY field value.
```
define("LOG_SERVICE_APP_UNICID", "4F59CB0433Z1C221869268") ;
```

  * Using the previous method find your api key and api secret to change their respective constant value.
```
//api key
define("LOG_SERVICE_API_KEY", "4F59695A496E4853357468") ;

//api secret
define("LOG_SERVICE_API_SECRET", "-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWx44LOUDFY34hAALwMl1cv576
nRzMKdPflXb78pLMexHHxMNJhS766IOpAgLoIxxewXdmOL8I6/qZFK3SsGQ6GPRM
beYARyzGumNBOFJVwOYTpwEjydUVgMvRTq3gH5oNHrl48Y0kN+u4J9DxY0OkiGlj
aytv+5CvPfhGxZ4+DQIDAQAB
-----END PUBLIC KEY-----");

```


  * Persiste. php sdk uses curl to communicate with the server by default; but when cur is not available, it uses the url of log file specified in the application settings to get your logs.
If curl is enable on your server you can skip this step.
If not, the file you specify here must be writable by your app and must be accessible through the web.
**NOTE:** don't worry the file is always cleaned after each request so no one will view your logs.

```
//local log file when curl is not avaible
define("LOG_SERVICE_FILE", "log.txt") ;

```

That's all for the integration.

## Persiste. API ##

The Persiste. API has three main methods which all return a response of type `LogServiceResponse`. This class has three properties:
  * Status: wich can take one of the following value:
    1. error : the call was not sent to the server for reason usually specified in the message property
    1. failure : The call was well sent to the server but was not performed for reseon that is usually specified in the message property.
    1. success : The call was performed correctly.
  * Message: The description or reason of the status
  * Data : An optional data sent from the server: usualy the logs you retrieved using get method.
### The get method (GET) ###
The get method allow you to retrieve logs previously saved.
This method takes a parameter which is the page of logs you intend to retrieve. it defaults to 1 when not specified.

**NOTE:** for performance reason on your application, persiste. only sends 12 logs per page.

The following is an example of calling the get method
```
<?php
$response=LogServiceClient::get(1);
var_dump($response);
```

### The delete method (DELETE) ###
The delete method allow you to delete a log by specifying its id.
Note that the log you want to delete must belong to the calling application otherwise you will get a failure status.

The following is an example of calling the delete method on a log with id (23)
```
<?php
$response=LogServiceClient::delete(23);
var_dump($response);
```

### The put method (PUT) ###
The put method is the heart of the api that allow you to save your logs or simply to log information on persiste. platform.
The sdk is purelly object oriented but provides an easy interface to reduce the amount of code you write.
Logs are classified into the following four categories:
  * error : for logging fatal errors or logs estimated at this level;
  * warn : for logging warnings or logs estimated at this level;
  * info : for logging information, alerts or logs estimated at this level;
  * success : for success alerts or logs estimated at this level;

The sdk provide an interface for each of this type of logs.
For each of these interface, you are required to provide the title and a description of the log.
  * You also can specify the developer emitting the log which allow you to spot from whose code the logs are coming from.
  * You also can specify a list of custom fields with their  value.
  * You can even specify a stack trace information.

The following is an example of sending a log.
```
<?php

//warning log with tile and description
$response=LogServiceClient::warn("my log", "sample log");

//information log with tile and description
$response=LogServiceClient::info("tell log", "sample log using tell");

//complete fatal error log with custom fields and stack trace
$fields=array('Line'=>'32','Function'=>'load','Memory'=>50);
$response=LogServiceClient::error("Evan Log", "sample log",'janedoe@yahoo.fr',$fields, 'this is the stack trace');

var_dump($response);
```