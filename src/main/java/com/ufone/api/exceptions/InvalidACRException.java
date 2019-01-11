// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidACRException extends Exception {
        private static final long serialVersionUID = 180428401293L;
        public InvalidACRException() {
                super("MANDATORY parameter acr_values is missing or invalid");
        }
}
