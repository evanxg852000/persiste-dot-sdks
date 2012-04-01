package com.persiste.sdk;

import com.persiste.sdk.json.JSONObject;

public interface INotifiable {
	public void LoggingClientCompleted(JSONObject response,boolean http_error_flag);
}
