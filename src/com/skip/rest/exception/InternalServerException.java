package com.skip.rest.exception;


public class InternalServerException extends BaseWebApplicationException {

    public InternalServerException() {
        super(500, "50001", "Internal server error", "Please see server log");
    }
}
