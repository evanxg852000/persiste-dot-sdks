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

            LogServiceClient.Initialise(new LogNotifactionReceiver(), true);
            //LogServiceClient.warn("C#", "a log from c#", "janedoe@yahoo.fr");
            // LogServiceClient.get();
            LogServiceClient.delete(24);

            Console.WriteLine("main");

        }

    }
}
