﻿/**
* Persiste. C# SDK
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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace PersisteDotSdk {

    public class Put : CallableMethod{
	    private Log log;
	
	    public Put(Log log) {
		    this.log=log;
	    }
	
	    public override void call() {
		    String url=Sdk.LOG_SERVICE_ENDPOINT+"put/"+Sdk.LOG_SERVICE_APP_UNICID+"/"+Sdk.LOG_SERVICE_API_KEY;
		    HttpClientWrapper http=new HttpClientWrapper(url);

            Dictionary<String, String> data = new Dictionary<String, String>();
		    data.Add("content", JsonConvert.SerializeObject(this.log));
		
		    http.post(data);
	    }

    }

}
