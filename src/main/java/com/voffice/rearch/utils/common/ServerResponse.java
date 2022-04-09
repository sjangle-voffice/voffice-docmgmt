package com.voffice.rearch.utils.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Generic response class for all VOffice API requests.
 * @author Sagar Jangle
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ServerResponse {

    private String message;
    private Integer httpStatusCode;
    private Object response;
    private String vofficeExceptionCode;
    private ExceptionResponse exceptionResponse;


    public ServerResponse(Object response, Integer httpStatusCode) {
        this.response = response;
        this.httpStatusCode = httpStatusCode;
    }

    public ServerResponse(String message, Integer httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public ServerResponse(String message, ExceptionResponse exceptionResponse) {
        this.message = message;
        this.exceptionResponse = exceptionResponse;
    }

    public ServerResponse(String message, Object response, Integer httpStatusCode) {
        this.message = message;
        this.response = response;
        this.httpStatusCode = httpStatusCode;
    }

    public ServerResponse(String message,
        String vofficeExceptionCode) {
        this.message = message;
        this.vofficeExceptionCode = vofficeExceptionCode;
    }

    public ServerResponse(Object response) {
        this.response = response;
    }

    public static ServerResponse getSuccessResponse(Object response, Integer httpStatusCode) {
        return new ServerResponse(response, httpStatusCode);
    }

    public static ServerResponse getSuccessResponse(Object response) {
        return new ServerResponse(response);
    }

    public static ServerResponse getErrorResponse(String message, Integer httpStatusCode,
        Object response) {
        return new ServerResponse(message, response, httpStatusCode);
    }

    public static ServerResponse getErrorResponse(String message, Integer httpStatusCode) {
        return new ServerResponse(message, httpStatusCode);
    }

    public static ServerResponse getErrorResponse(String message, ExceptionResponse exception) {
        return new ServerResponse(message, exception);
    }

    public static ServerResponse getErrorResponse(String message, String cdiExceptionCode) {
        return new ServerResponse(message, cdiExceptionCode);
    }
}