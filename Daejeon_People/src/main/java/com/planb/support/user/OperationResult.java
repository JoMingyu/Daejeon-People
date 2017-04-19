package com.planb.support.user;

public class OperationResult {
	private String message;
	private boolean success;
	
	public OperationResult() {
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
