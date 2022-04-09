package com.voffice.rearch.utils.misc;

public enum RecordStatusEnum {

    REC_CREATE_STATUS("create"),
    REC_DELETE_STATUS("delete");

    private String url;

    RecordStatusEnum(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }
}