package com.altran.shoppingcart.exceptions;

public class NoContentException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NoContentException(final String message) {
		super(message);
	}

}
