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

namespace PersisteDotSdk {

    public class Sdk {
        /* The log server url (do not change this ) */
	    public static String LOG_SERVICE_ENDPOINT="http://flix.dev/services/csharp/logs/" ;

	    /* The application unic id */
        public static String LOG_SERVICE_APP_UNICID = "";

	    /* Your api key */
        public static String LOG_SERVICE_API_KEY = "";

	    /*Your api secret*/
        public static String LOG_SERVICE_API_SECRET = "not in use in this sdk";
	
    }

}
