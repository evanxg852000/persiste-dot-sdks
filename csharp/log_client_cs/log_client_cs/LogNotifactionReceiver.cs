using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersisteDotSdk;

namespace log_client_cs {
    class LogNotifactionReceiver : INotifiable {

        public void LoggingClientCompleted(LogServiceResponse response, bool http_error_flag) {

            Console.Write("response");
        }
    }
}
