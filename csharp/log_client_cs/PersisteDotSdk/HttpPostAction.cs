using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;

namespace PersisteDotSdk {
    public class HttpPostAction {
        private HttpClientWrapper parent;
        private string url;
        private Dictionary<String, String> data;
        private String response;
        public bool has_error;

        public HttpPostAction(HttpClientWrapper parent, String url,Dictionary<String,String>data) {
            this.parent = parent;
            this.url = url;
            this.data = data;
            this.has_error = false;
        }

        public void Run() {
            try {
                //do post request here
                //http://devlicio.us/blogs/derik_whittaker/archive/2009/02/14/posting-data-to-a-rest-service-using-c.aspx
            }
            catch (Exception e) {
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
