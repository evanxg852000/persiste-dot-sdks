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
using System.Net;

namespace PersisteDotSdk {
    public class HttpGetAction {
        private HttpClientWrapper parent;
        private string url;
        private String response;
        public bool has_error;

        public HttpGetAction(HttpClientWrapper parent,String url) {
            this.parent = parent;
            this.url = url;
            this.response = "";
            this.has_error = false;
        }

        public void Run() {
            try {
                using (WebClient client = new WebClient()) {
                    this.response = client.DownloadString(this.url);
                }
            }
            catch (Exception) {
                this.response = "{\"status\":\"failure\",\"message\":\"Http client error check connection\"}";
                this.has_error = true;
            }

            if (HttpClientWrapper.getNotificationReceiver() != null) {
                LogServiceResponse formated_response = this.parent.unpackData(this.response);
                HttpClientWrapper.getNotificationReceiver().LoggingClientCompleted(formated_response, this.has_error);
            }		
        }

    }
}
