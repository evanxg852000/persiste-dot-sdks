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
    public class Delete : CallableMethod {
        private int id;

        public Delete(int id) {
            this.id = id;
        }


        public override void call() {
            String url = Sdk.LOG_SERVICE_ENDPOINT + "delete/" + Sdk.LOG_SERVICE_APP_UNICID + "/" + Sdk.LOG_SERVICE_API_KEY + "/" + this.id;
            HttpClientWrapper http = new HttpClientWrapper(url);

            http.get();
        }

    }

}
