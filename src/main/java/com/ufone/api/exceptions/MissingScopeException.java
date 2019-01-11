// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class MissingScopeException extends Exception {
        private static final long serialVersionUID = 91859124L;
        public MissingScopeException() {
                super("MANDATORY parameter state is missing");
        }
}
