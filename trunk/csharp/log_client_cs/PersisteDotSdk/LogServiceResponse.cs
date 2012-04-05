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
    public class LogServiceResponse {
        public String Status { get; set; }
        public String Message { get; set; }
        public List<Object>  Data {get;set;}

        public LogServiceResponse(String status, String message) {
            this.Status=status;
            this.Message = message;
            this.Data = null;
        }

    }
}
