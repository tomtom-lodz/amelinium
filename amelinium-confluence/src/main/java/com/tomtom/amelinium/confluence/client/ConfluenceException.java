package com.tomtom.amelinium.confluence.client;

public class ConfluenceException extends RuntimeException {

	private static final long serialVersionUID = -7891794884647897475L;

	public ConfluenceException(String message) {
		super(message);
	}

	public ConfluenceException(Throwable cause) {
		super(cause);
	}
	
	public ConfluenceException(String message, Throwable cause) {
		super(message,cause);
	}
}
