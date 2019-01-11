// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidStateException extends Exception {
        private static final long serialVersionUID = 8512983L;
        public InvalidStateException() {
                super("MANDATORY parameter state is missing or invalid");
        }
}
