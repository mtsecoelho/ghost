package br.com.splnet.ghost.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.ObjectError;

import lombok.Data;

@Data
public class ResponseModel {

	private int status;
	private List<String> messages;
	private Object data;
	
	public ResponseModel(int status, String message, Object data) {
		this.status = status;
		
		this.messages = new ArrayList<String>();
		this.messages.add(message);
		
		this.data = data;
	}
	
//	public ResponseModel(int status, List<String> messages, Object data) {
//		this.status = status;
//		this.messages = messages;
//		this.data = data;
//	}
	
	public ResponseModel(int status, List<ObjectError> objectErrors, Object data) {
		this.status = status;
		
		this.messages = new ArrayList<String>();
		for (ObjectError objectError : objectErrors) {
			this.messages.add(objectError.getDefaultMessage());
		}
		
		this.data = data;
	}
}
