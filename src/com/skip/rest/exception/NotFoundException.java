package com.skip.rest.exception;

import javax.ws.rs.WebApplicationException;

public class NotFoundException extends WebApplicationException {

    public NotFoundException() {
        super(404);
    }
}
