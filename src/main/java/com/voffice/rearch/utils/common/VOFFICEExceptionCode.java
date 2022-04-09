package com.voffice.rearch.utils.common;

import lombok.Getter;

/**
 * Custom exception codes for VOfice.
 * @author Sagar Jangle.
 */
@Getter
public enum VOFFICEExceptionCode {

    DATA_NOT_FOUND_EXCEPTION("EXP-OFF-100", "Data not found"),
    DUPLICATE_DATA_EXCEPTION("EXP-OFF-101", "Duplicate data found"),
    INVALID_OPERATION_EXCEPTION("EXP-OFF-102", "Selected operation cannot be performed."),
    EMAIL_PROCESS_EXCEPTION("EXP-OFF-103", "Error occurred while sending an email."),
    CONNECTION_EXCEPTION("EXP-OFF-104", "Error while connecting to any of the servers/services."),
    UNHANDLED_EXCEPTION("EXP-OFF-105", "Unhandled exception occurred ,please contact support."),
    DATABASE_CONSTRAINT_EXCEPTION("EXP-OFF-106", "Database constraint violated."),
    USER_INPUT_VALIDATION_EXCEPTION("EXP-OFF-107", "User input invalid."),
    RESOURCE_NOT_FOUND_EXCEPTION("EXP-OFF-108", "Resource not found."),
    DATA_CONFIGURATION_EXCEPTION("EXP-OFF-108", "Data configuration error"),
    ACCESS_DENIED_EXCEPTION("EXP-OFF-109", "Access Denied for the user"),
    OPERATION_FAILED_EXCEPTION("EXP-OFF-111", "Operation failed."),
    DATE_PARSE_EXCEPTION("EXP-OFF-110", "Error parsing date from string"),
    AUTH_FAILURE_EXCEPTION("EXP-OFF-113", "Authentication Failure"),
    INVALID_DATA_EXCEPTION("EXP-OFF-112", "Data invalid.");

    private final String code;
    private final String message;

    VOFFICEExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}