using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PersisteDotSdk {
    public interface INotifiable {
        void LoggingClientCompleted(LogServiceResponse response, bool http_error_flag);
    }
}
