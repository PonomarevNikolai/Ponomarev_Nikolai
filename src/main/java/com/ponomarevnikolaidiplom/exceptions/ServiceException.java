package com.ponomarevnikolaidiplom.exceptions;

import com.ponomarevnikolaidiplom.exceptionhandler.TypicalError;
import lombok.Getter;

@Getter
public class ServiceException extends Exception {
    private final TypicalError typicalError;

    public ServiceException(String message, TypicalError typicalError) {
        super(message);
        this.typicalError = typicalError;
    }
}
