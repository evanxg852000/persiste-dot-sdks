/**
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

namespace PersisteDotSdk {

   public class Get: CallableMethod {
        private int page;
	
	    public Get() {
		    this.page=1;
	    }
	
	    public Get(int page) {
		    this.page=page;
	    }
	
	    public override void call() {
		    String url=Sdk.LOG_SERVICE_ENDPOINT+"get/"+Sdk.LOG_SERVICE_APP_UNICID+"/"+Sdk.LOG_SERVICE_API_KEY+"/"+this.page;
		    HttpClientWrapper http=new HttpClientWrapper(url);
		    http.get();
	    }

    }

}
