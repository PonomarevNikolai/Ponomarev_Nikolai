package com.ponomarevnikolaidiplom.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TypicalError {
    NOT_FOUND(HttpStatus.NOT_FOUND),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    TOO_YOUNG(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE),
    EXPIRE(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE),
    DUPLICATE(HttpStatus.NOT_ACCEPTABLE),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status;

    TypicalError(HttpStatus status) {
        this.status = status;
    }
}
