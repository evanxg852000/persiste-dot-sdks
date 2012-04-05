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
using System.Web;
using System.IO;

namespace PersisteDotSdk {
    public class HttpPostAction {
        private HttpClientWrapper parent;
        private string url;
        private String data;
        private String response;
        public bool has_error;

        public HttpPostAction(HttpClientWrapper parent, String url,Dictionary<String,String>data) {
            this.parent = parent;
            this.url = url;
            this.response = "";
            this.has_error = false;
            //init post data
            StringBuilder sb = new StringBuilder();
            foreach (KeyValuePair<String, String> item in data) {
                sb.AppendFormat("{0}={1}", item.Key, HttpUtility.UrlEncode(item.Value));
                sb.Append("&");
            }
            this.data = sb.ToString().Trim(new char[] { ' ', '&' });
        }

        public void Run() {
            try {
                //init request
                HttpWebRequest httpWebRequest = HttpWebRequest.Create(this.url) as HttpWebRequest;
                httpWebRequest.Method = "POST";
                httpWebRequest.ContentLength = this.data.Length;
                httpWebRequest.ContentType = "application/x-www-form-urlencoded";
                using (StreamWriter sw = new StreamWriter(httpWebRequest.GetRequestStream())) {
                    sw.Write(this.data);
                }
                //get response 
                HttpWebResponse httpWebResponse = httpWebRequest.GetResponse() as HttpWebResponse;
                using (StreamReader sr = new StreamReader(httpWebResponse.GetResponseStream())) {
                     this.response = sr.ReadToEnd();
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
