package com.persiste.sdk;

import java.util.ArrayList;

public class LogserviceResponse {
	private String status;
	private String message;
	private ArrayList<Object> data;
	
	
	public LogserviceResponse(String status, String message) {
		this.status=status;
		this.message=message;
	}
	
	public void setData(ArrayList<Object> data){
		this.data=data;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public ArrayList<Object> getData(){
		return this.data;
	}
		
}
