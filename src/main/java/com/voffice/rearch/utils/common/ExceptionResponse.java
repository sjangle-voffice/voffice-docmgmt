package com.voffice.rearch.utils.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.voffice.rearch.utils.exception.model.BaseException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO for holding VOffice Custom exception details.
 * Will be embedded in main server response.
 * @author Sagar Jangle.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ExceptionResponse {

    String voffExceptionCode;
    String voffExceptionDesc;
    String domainType;

    public ExceptionResponse(BaseException e) {
        this.voffExceptionCode = e.getVofficeExceptionCode().getCode();
        this.voffExceptionDesc = e.getVofficeExceptionCode().getMessage();
        this.domainType = e.getDomainType() != null ? e.getDomainType().name() : null;
    }

    public ExceptionResponse(String voffExceptionCode, String voffExceptionDesc) {
        this.voffExceptionCode = voffExceptionCode;
        this.voffExceptionDesc = voffExceptionDesc;
    }

    @Override
    public String toString() {
        return "ExceptionResponse [VOfficeExceptionCode=" + voffExceptionCode + ", VOfficeExceptionDesc="
            + voffExceptionDesc + ", domainType=" + domainType + "]";
    }
}