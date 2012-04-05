using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;
using System.Net;
using System.Threading;

namespace PersisteDotSdk {
    
    public class HttpClientWrapper {
        private static INotifiable notification_receiver;
	    private static bool is_async;
	

        public String url; 
	
	    public HttpClientWrapper(String url) {
		    this.url=url;
	    }
	
	    public void get() {
            HttpGetAction action=new HttpGetAction(this,this.url);
		    if(HttpClientWrapper.is_async==true){
			    Thread thread = new Thread(action.Run);
			    thread.Start();
		    }else{
			    action.Run();
		    }
	    } 
		
	    public void post( Dictionary<String, String> data) {
		    HttpPostAction action=new HttpPostAction(this,this.url, data);
		    if(HttpClientWrapper.is_async==true){
			    Thread thread = new Thread(action.Run);
			    thread.Start();
		    }else{
			    action.Run();
		    }
	    } 
	
	    public String packData(Dictionary<String, Object> data){
		    return null;
	    }

        public LogServiceResponse unpackData(String response) {
            LogServiceResponse formated_response = null;
		    if( String.Equals(response,"404") ){
                formated_response= new LogServiceResponse("failure","Missing required parameter");
			    return formated_response;
		    }

            if (String.Equals(response, "401")) {
                formated_response = new LogServiceResponse("failure", "Authentication error, check your credentials");
                return formated_response;
		    }
            
            try { 
                formated_response=JsonConvert.DeserializeObject<LogServiceResponse>(response);

                if (formated_response.Data != null) {
                    Log log = null;
                    int level;
                    String title;
                    String description;
                    String emiter;
                    Dictionary<String, String> custom_fields;
                    String stack_trace;

                    List<Object> log_list = new List<object>();
                    Newtonsoft.Json.Linq.JToken value;
                    foreach (System.Collections.Generic.ICollection<System.Collections.Generic.KeyValuePair<System.String, Newtonsoft.Json.Linq.JToken>> item in formated_response.Data) {
                        var p = item.ToDictionary((keyItem) => keyItem.Key, (valueItem) => valueItem.Value);
                        value = (p.TryGetValue("level", out value)) ? value : null;
                        level = Int16.Parse((String)value);
                        value = (p.TryGetValue("title", out value)) ? value : null;
                        title = (String)value;
                        value = (p.TryGetValue("description", out value)) ? value : null;
                        description = (String)value;
                        value = (p.TryGetValue("emiter_email", out value)) ? value : null;
                        emiter = (String)value;
                        //we dont send back custome field for now
                        //value = (p.TryGetValue("custom_fields", out value)) ? value : null;
                        custom_fields = null;
                        value = (p.TryGetValue("stack_trace", out value)) ? value : null;
                        stack_trace = (String)value;

                        log = new Log(level, title, description, emiter, custom_fields, stack_trace);
                        log_list.Add(log);

                    }
                    formated_response.Data = log_list;
                }

            }catch(Exception){}

            return formated_response;
	    }

   
        public static bool isAsync(){
		    return HttpClientWrapper.is_async;		
	    }

        public static INotifiable getNotificationReceiver() {
            return HttpClientWrapper.notification_receiver;
        }

	    public static void setParams(INotifiable  notification_receiver,bool is_async){
		    HttpClientWrapper.notification_receiver=notification_receiver;
		    HttpClientWrapper.is_async=is_async;
	    }
		 
    }


    



}
