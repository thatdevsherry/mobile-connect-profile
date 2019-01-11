// Copyright 2019 Shehriyar Qureshi
package com.ufone.api.exceptions;

public class InvalidRedirectURIException extends Exception {
        private static final long serialVersionUID = 1981823L;
        public InvalidRedirectURIException() {
                super("redirect_uri is invalid");
        }
}
