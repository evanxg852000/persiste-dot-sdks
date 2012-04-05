using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PersisteDotSdk {
    public class Log {
        public int level;
        public String title;
        public String description;
        public String emiter;
        public Dictionary<String, String> custom_fields;
        public String stack_trace;

        public Log(int level, String title, String description, String emiter, Dictionary<String, String> custom_fields, String stack_trace) {
            this.level = level;
            this.title = title;
            this.description = description;
            this.stack_trace = stack_trace;
            this.custom_fields = custom_fields;
            this.emiter = emiter;
        }

        public Log(int level, String title, String description, String emiter, Dictionary<String, String> custom_fields) {
            this.level = level;
            this.title = title;
            this.description = description;
            this.stack_trace = "";
            this.custom_fields = custom_fields;
            this.emiter = emiter;
        }

        public Log(int level, String title, String description, String emiter) {
            this.level = level;
            this.title = title;
            this.description = description;
            this.stack_trace = "";
            this.custom_fields = new Dictionary<String, String>();
            this.emiter = emiter;
        }

        public Log(int level, String title, String description) {
            this.level = level;
            this.title = title;
            this.description = description;
            this.stack_trace = "";
            this.custom_fields = new Dictionary<String, String>();
            this.emiter = "";
        }


    }
}
