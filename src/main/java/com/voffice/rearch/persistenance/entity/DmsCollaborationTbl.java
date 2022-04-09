package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "dms_collaboration_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class DmsCollaborationTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dms_collaboration_id")
    int dmsCollaborationId;

    @Column(name = "dms_collaboration_type")
    String dmsCollaborationType;

    @Column(name = "source_of_record")
    String sourceOfRecord;

    @Column(name = "community_id")
    String communityId;

    @Column(name = "member_id")
    String memberId;

    @Column(name = "dms_collaboration_title")
    String dmsCollaborationTitle;

    @Column(name = "description")
    String description;

    @Column(name = "dms_collaboration_tags")
    String dmsCollaborationTags;

    @Column(name = "dms_collaboration_access")
    String dmsCollaborationAccess;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "total_share_member_count")
    String totalShareMemberCount;

    @Column(name = "total_comment")
    String totalComment;

    @Column(name = "total_likes")
    String totalLikes;

    @Column(name = "last_comment")
    String lastComment;

    @Column(name = "last_comment_by")
    String lastCommentBy;

    @Column(name = "last_comment_on")
    String lastCommentOn;

    @Column(name = "discussion_created")
    String discussionCreated;

    @Column(name = "discussion_status")
    String discussionStatus;

    @Column(name = "creation_source")
    String creationSource;

    @Column(name = "document_type")
    String documentType;

    @Column(name = "inward_outward_date")
    String inwardOutwardDate;

    @Column(name = "inwardfrom_outwardto")
    String inwardFromOutwardto;

    @Column(name = "inwardto_outwatdfrom")
    String inwardToOutwatdfrom;

    @Column(name = "is_project_related")
    String isProjectRelated;

    @Column(name = "project_name")
    String projectName;

    @Column(name = "department_name")
    String departmentName;

    @Column(name = "remark")
    String remark;

    @Column(name = "secretarial_tag")
    String secretarialTag;

    @Column(name = "isviewed")
    String isViewed;

    @Column(name = "dms_delete_status")
    String dmsDeleteStatus;

    @Column(name = "sub_creation_source")
    String subCreationSource;

    @Column(name = "competitor_name")
    String competitorName;

    @Column(name = "customer_through")
    String customerThrough;

    @Column(name = "distributor")
    String distributor;

    @Column(name = "sub_distributor")
    String subDistributor;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "sub_customer_name")
    String subCustomerName;

    @Column(name = "plant")
    String plant;

    @Column(name = "doc_related")
    String docRelated;
}