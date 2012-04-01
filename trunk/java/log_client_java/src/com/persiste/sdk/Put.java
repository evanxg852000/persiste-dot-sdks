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

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.persiste.sdk.json.JSONObject;

public class Put extends CallableMethod{
	private JSONObject log;
	
	@SuppressWarnings("unchecked")
	public Put(Log log) {
		this.log=new JSONObject();
		this.log.put("level", new Integer(log.level));
		this.log.put("title", log.title);
		this.log.put("description", log.description);
		this.log.put("emiter", log.emiter);
		this.log.put("custom_fields", log.custom_fields);
		this.log.put("stack_trace", log.stack_trace);
	}
	
	@Override
	public void call() {
		String url=Sdk.LOG_SERVICE_ENDPOINT+"put/"+Sdk.LOG_SERVICE_APP_UNICID+"/"+Sdk.LOG_SERVICE_API_KEY;
		HttpClientWrapper http=new HttpClientWrapper(url);
		
		ArrayList<NameValuePair> data=new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("content", this.log.toJSONString()));
		http.post(data);
	}

}
