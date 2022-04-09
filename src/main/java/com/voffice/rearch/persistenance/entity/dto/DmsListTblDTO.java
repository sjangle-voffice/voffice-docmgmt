package com.voffice.rearch.persistenance.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DmsListTblDTO {

    private int documentId;
    private String parentDocId;
    private String dmsCollaborationId;
    private String sourceOfRecord;
    private String sourceOfRecordVersion;
    private String originalName;
    private String filePath;
    private String version;
    private String finalVersion;
    private String checkInOutBy;
    private String checkInOutOn;
    private String createdBy;
    private String createdOn;
    private String deleteStatus;
    private String sourceOfCreation;
    private String documentText;
    private String markTo;
    private String markingRemark;
    private String projectName;
    private String departmentName;
    private String markingDate;
    private String markedBy;
    private String markFlag;
    private String projectDetailsAttach;
}