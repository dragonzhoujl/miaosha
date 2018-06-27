package com.qiyue.miaosha.exception;

public class SecKillCloseException extends RuntimeException{
	
	public SecKillCloseException(String message) {
		super(message);
	}
	
	public SecKillCloseException(String message,Throwable cause) {
		super(message,cause);
	}

}
