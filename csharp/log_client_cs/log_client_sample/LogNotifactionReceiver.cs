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
using PersisteDotSdk;

namespace log_client_sample {
    class LogNotifactionReceiver : INotifiable {

        public void LoggingClientCompleted(LogServiceResponse response, bool http_error_flag) {
            //check service call status here an maybe report errors
            if (response != null) { 
                Console.WriteLine(response.Status);
                Console.WriteLine(response.Message);   
            }

        }


    }
}
