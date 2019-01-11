// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class MissingClientIDException extends Exception {
        private static final long serialVersionUID = 291092L;
        public MissingClientIDException() {
                super("MANDATORY parameter client_id is missing");
        }
}
