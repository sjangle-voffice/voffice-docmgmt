package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "dms_list_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class DmsListTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    int documentId;

    @Column(name = "parent_doc_id")
    String parentDocId;

    @Column(name = "dms_collaboration_id")
    String dmsCollaborationId;

    @Column(name = "source_of_record")
    String sourceOfRecord;

    @Column(name = "source_of_record_version")
    String sourceOfRecordVersion;

    @Column(name = "original_name")
    String originalName;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "version")
    String version;

    @Column(name = "final_version")
    String finalVersion;

    @Column(name = "check_in_out_by")
    String checkInOutBy;

    @Column(name = "check_in_out_on")
    String checkInOutOn;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "delete_status")
    String deleteStatus;

    @Column(name = "sourceof_creation")
    String sourceOfCreation;

    @Column(name = "document_text")
    String documentText;

    @Column(name = "mark_to")
    String markTo;

    @Column(name = "marking_remark")
    String markingRemark;

    @Column(name = "project_name")
    String projectName;

    @Column(name = "department_name")
    String departmentName;

    @Column(name = "marking_date")
    String markingDate;

    @Column(name = "marked_by")
    String markedBy;

    @Column(name = "mark_flag")
    String markFlag;

    @Column(name = "project_details_attach")
    String projectDetailsAttach;
}