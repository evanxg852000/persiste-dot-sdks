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

public class Log {
    public int level;
    public String title;
    public String description;
    public String emiter;
    public Map<String, String> custom_fields;
    public String stack_trace;
    
    public Log(int level, String title, String description,String emiter,Map<String, String> custom_fields, String stack_trace){
    	this.level= level;
        this.title= title;
         this.description= description;
         this.stack_trace= stack_trace;
         this.custom_fields= custom_fields;
         this.emiter= emiter;
    }
    
    public Log(int level, String title, String description,String emiter,Map<String, String> custom_fields){
    	 this.level= level;
         this.title= title;
         this.description= description;
         this.stack_trace= "";
         this.custom_fields= custom_fields;
         this.emiter= emiter;
    }
    
    public Log(int level, String title, String description,String emiter){
   	 this.level= level;
        this.title= title;
        this.description= description;
        this.stack_trace= "";
        this.custom_fields= new HashMap<String, String>();
        this.emiter= emiter;
    }  
    
    public Log(int level, String title, String description){
    	this.level= level;
    	this.title= title;
    	this.description= description;
    	this.stack_trace= "";
    	this.custom_fields= new HashMap<String, String>();
    	this.emiter= "";
    }  


}