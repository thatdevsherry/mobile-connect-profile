// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidGrantTypeException extends Exception {
        private static final long serialVersionUID = 9018410293L;
        public InvalidGrantTypeException() {
                super("grant_type is invalid");
        }
}
