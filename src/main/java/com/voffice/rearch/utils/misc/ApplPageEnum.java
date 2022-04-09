package com.voffice.rearch.utils.misc;

public enum ApplPageEnum {

    SRCH_DOC("SEARCH_DOCUMENT"),
    UPLD_DOC("UPLOAD_DOCUMENT");

    private String url;

    ApplPageEnum(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}