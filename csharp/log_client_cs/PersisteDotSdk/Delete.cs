using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PersisteDotSdk {
    public class Delete : CallableMethod {
        private int id;

        public Delete(int id) {
            this.id = id;
        }


        public override void call() {
            String url = Sdk.LOG_SERVICE_ENDPOINT + "put/" + Sdk.LOG_SERVICE_APP_UNICID + "/" + Sdk.LOG_SERVICE_API_KEY + "/" + this.id;
            HttpClientWrapper http = new HttpClientWrapper(url);

            http.get();
        }

    }

}
