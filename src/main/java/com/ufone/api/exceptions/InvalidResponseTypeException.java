// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidResponseTypeException extends Exception {
        private static final long serialVersionUID = 958123L;
        public InvalidResponseTypeException() {
                super("MANDATORY parameter response_type is missing or invalid");
        }
}
