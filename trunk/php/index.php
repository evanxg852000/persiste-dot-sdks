<?php 
include 'sdk/Sdk.php';

$response=null;
//$response=LogServiceClient::warn("my log", "sample log");
//$response=LogServiceClient::warn("tell log", "sample log using tell");
//$response=LogServiceClient::get(1);
//$response=LogServiceClient::delete(15);
/*
$f=array('Line'=>'32','Function'=>'load','Memory'=>50);
$response=LogServiceClient::warn("Evan Log", "sample log",'evanxg852000@yahoo.fr',$f);
*/
var_dump(json_decode($response));		

