package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "document_type_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class DocumentTypeTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    Integer documentId;

    @Column(name = "document_name")
    String documentName;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "created_by")
    String createdBy;
}