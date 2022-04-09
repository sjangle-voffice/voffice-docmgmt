package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "document_source", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class DocumentSourceTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "src_id")
    Integer srcId;

    @Column(name = "doc_description")
    String docDescription;

    @Column(name = "doc_ui_desc")
    String docUiDesc;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "created_by")
    String createdBy;
}