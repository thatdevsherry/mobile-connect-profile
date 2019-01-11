// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidVersionException extends Exception {
        private static final long serialVersionUID = 185024L;
        public InvalidVersionException() {
                super("MANDATORY parameter version is missing or invalid");
        }
}
