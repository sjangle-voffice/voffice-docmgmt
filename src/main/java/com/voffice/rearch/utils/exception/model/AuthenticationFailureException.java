package com.voffice.rearch.utils.exception.model;

import com.voffice.rearch.utils.common.DomainType;
import com.voffice.rearch.utils.common.JobDetails;
import com.voffice.rearch.utils.common.VOFFICEExceptionCode;

/**
 * Exception thrown when user performs an operation that cannot be completed becuase application is
 * trying to access a resoource that is not available.
 * @author Sagar Jangle.
 */
public class AuthenticationFailureException extends BaseException {

    public AuthenticationFailureException(String message, DomainType domainType,
                                          VOFFICEExceptionCode VOFFICEExceptionCode, Exception exception) {
        super(message, domainType, VOFFICEExceptionCode, exception);
    }

    public AuthenticationFailureException(String message, DomainType domainType,
                                          VOFFICEExceptionCode VOFFICEExceptionCode, JobDetails jobDetails, Exception exception) {
        super(message, domainType, VOFFICEExceptionCode, jobDetails, exception);
    }

    public AuthenticationFailureException(String message, DomainType domainType,
                                          VOFFICEExceptionCode VOFFICEExceptionCode, JobDetails jobDetails) {
        super(message, domainType, VOFFICEExceptionCode, jobDetails);
    }

    public AuthenticationFailureException(String message, DomainType domainType,
                                          VOFFICEExceptionCode VOFFICEExceptionCode) {
        super(message, domainType, VOFFICEExceptionCode);
    }
}