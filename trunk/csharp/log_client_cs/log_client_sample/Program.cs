using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersisteDotSdk;

namespace log_client_sample {
    class Program {
        static void Main(string[] args) {
            LogServiceClient.setParams(new LogNotifactionReceiver(), true);
            LogServiceClient.get();
           
            HttpClientWrapper c = new HttpClientWrapper("http://evansofts.com");
           // c.get();
           

            Console.Write("main");

        }

    }
}
