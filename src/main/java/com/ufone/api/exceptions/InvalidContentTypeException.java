// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidContentTypeException extends Exception {
	private static final long serialVersionUID = 901841092L;
	public InvalidContentTypeException() {
		super("Content type is not application/x-www-form-urlencoded");
	}
}
