// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidPromptException extends Exception {
        private static final long serialVersionUID = 9128401L;
        public InvalidPromptException() {
                super("MANDATORY parameter prompt is missing or invalid");
        }
}
