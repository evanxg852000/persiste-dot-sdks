using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersisteDotSdk;

namespace log_client_sample {
    class LogNotifactionReceiver : INotifiable {

        public void LoggingClientCompleted(LogServiceResponse response, bool http_error_flag) {
            if (response != null) {
              //  System.Collections.Generic.KeyValuePair<String,String>
                Console.Write(response.Message);
            } 
        }


    }
}
