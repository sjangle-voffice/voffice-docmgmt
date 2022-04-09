package com.voffice.rearch.utils.exception.handler;

import com.voffice.rearch.utils.common.ExceptionResponse;
import com.voffice.rearch.utils.common.ServerResponse;
import com.voffice.rearch.utils.common.VOFFICEExceptionCode;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Sagar Jangle.
 * <p>
 * Global exception handler, it is going to intercept all the REST API exceptions from controllers
 * across the application. This will be updated as and when new exceptions are added.
 */
@ControllerAdvice
public class GlobalAPIExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = Logger.getLogger(GlobalAPIExceptionHandler.class.getName());

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ServerResponse> handleAccessDeniedException(
            RuntimeException e) {
        LOG.log(Level.SEVERE,
            "Access Denied Exception Occurred " + ExceptionUtils.getStackTrace(e));

        ExceptionResponse exceptionResponse =
            new ExceptionResponse(VOFFICEExceptionCode.ACCESS_DENIED_EXCEPTION.getCode(),
                VOFFICEExceptionCode.ACCESS_DENIED_EXCEPTION.getMessage());
        ServerResponse serverResponse =
            ServerResponse.getErrorResponse(e.getMessage(), exceptionResponse);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(serverResponse);
    }
}