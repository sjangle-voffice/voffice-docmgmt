package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "community_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class CommunityTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    int communityId;

    @Column(name = "community_name")
    String communityName;

    @Column(name = "community_description")
    String communityDescription;

    @Column(name = "member_ids")
    String memberIds;

    @Column(name = "thread_count")
    String threadCount;

    @Column(name = "post_count")
    String postCount;

    @Column(name = "last_post")
    String lastPost;

    @Column(name = "last_post_on")
    String lastPostOn;

    @Column(name = "last_post_by")
    String lastPostBy;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "temp_delete")
    String tempDelete;
}