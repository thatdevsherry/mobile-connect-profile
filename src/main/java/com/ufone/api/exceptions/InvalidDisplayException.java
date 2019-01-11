// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidDisplayException extends Exception {
        private static final long serialVersionUID = 810820142L;
        public InvalidDisplayException() {
                super("MANDATORY parameter display is missing or invalid");
        }
}
