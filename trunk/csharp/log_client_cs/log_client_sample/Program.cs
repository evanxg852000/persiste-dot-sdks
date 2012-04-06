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
    class Program {
        static void Main(string[] args) {
            //configure
            Sdk.LOG_SERVICE_APP_UNICID = "4F59CB0433Z1C221869268";
            Sdk.LOG_SERVICE_API_KEY = "4F59695A496E4853357468";

            //initialise service callback (passing true will make all service call to be performed asynchronously) 
            LogServiceClient.Initialise(new LogNotifactionReceiver(), true);

            //save log with custom fields
            Dictionary<String, String> custom_fields=new Dictionary<string,string>();
            custom_fields.Add("Calibre", "58");
            custom_fields.Add("FPS", "110");
            LogServiceClient.warn("C#", "a log from c#", "janedoe@yahoo.fr",custom_fields);
            

            //save log without fields
            LogServiceClient.warn("C#", "a log from c#", "janedoe@yahoo.fr");

            //get logs by page (first page)
             LogServiceClient.get(1);
            
            //delete log
            LogServiceClient.delete(38);

        }

    }
}
