// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class MissingNonceException extends Exception {
        private static final long serialVersionUID = 8120131L;
        public MissingNonceException() {
                super("MANDATORY parameter nonce is missing");
        }
}
