package com.novi.DemoDrop.exceptions;

public class RecordNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;
    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
