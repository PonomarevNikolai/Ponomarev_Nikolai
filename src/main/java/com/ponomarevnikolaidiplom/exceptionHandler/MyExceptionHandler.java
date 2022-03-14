package com.ponomarevnikolaidiplom.exceptionHandler;

import com.ponomarevnikolaidiplom.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<ErrorMessage> handleConflict(ServiceException e) {
        log.error(e.getMessage());
        TypicalError typicalError = e.getTypicalError();
        return new ResponseEntity<>(new ErrorMessage("Ops! " + e.getMessage()), typicalError.getStatus());
    }
}
