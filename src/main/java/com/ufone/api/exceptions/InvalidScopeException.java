// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidScopeException extends Exception {
        private static final long serialVersionUID = 851820L;
        public InvalidScopeException() {
                super("MANDATORY parameter scope is missing or invalid");
        }
}
