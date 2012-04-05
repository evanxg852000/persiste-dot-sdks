using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using PersisteDotSdk;

namespace log_client_cs {
    static class Program {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main() {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
         //   Application.Run(new Form1());

            LogServiceClient.setParams(new LogNotifactionReceiver(), true);

            HttpClientWrapper c = new HttpClientWrapper("http://evansofts.com");
            c.get();
        }
    }
}
