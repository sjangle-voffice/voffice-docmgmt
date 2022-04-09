package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "master_tag_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class MasterTagTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    String tagId;

    @Column(name = "tag_name")
    String tagName;

    @Column(name = "temp_delete")
    String tempDelete;
}