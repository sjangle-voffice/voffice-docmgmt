package com.voffice.rearch.utils.exception.model;

import com.voffice.rearch.utils.common.DomainType;
import com.voffice.rearch.utils.common.JobDetails;
import com.voffice.rearch.utils.common.VOFFICEExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Root of all user created exceptions,all other exceptions to inherit from this.
 * @author Sagar Jangle.
 */
@Getter
@Setter
public class BaseException extends RuntimeException {

    // EPOCH time ,would be interpreted by UI according to timezone
    private long timestamp = System.currentTimeMillis() / 1000;
    private DomainType domainType;
    private VOFFICEExceptionCode vofficeExceptionCode;
    private JobDetails jobDetails;

    public BaseException(String message, DomainType domainType, VOFFICEExceptionCode vofficeExceptionCode) {
        super(message);
        this.domainType = domainType;
        this.vofficeExceptionCode = vofficeExceptionCode;
    }

    public BaseException(String message, DomainType domainType, VOFFICEExceptionCode vofficeExceptionCode,
        Exception exception) {
        super(message, exception);
        this.domainType = domainType;
        this.vofficeExceptionCode = vofficeExceptionCode;
    }

    public BaseException(String message, DomainType domainType, VOFFICEExceptionCode vofficeExceptionCode,
        JobDetails jobDetails, Exception exception) {
        super(message, exception);
        this.domainType = domainType;
        this.vofficeExceptionCode = vofficeExceptionCode;
    }

    public BaseException(String message, DomainType domainType, VOFFICEExceptionCode vofficeExceptionCode,
        JobDetails jobDetails) {
        super(message);
        this.domainType = domainType;
        this.vofficeExceptionCode = vofficeExceptionCode;
    }
}