package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "notification_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class NotificationsTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    int notificationId;

    @Column(name = "notification_to")
    String notificationTo;

    @Column(name = "community_id")
    String communityId;

    @Column(name = "notification_viewed")
    String notificationViewed;

    @Column(name = "notification_details")
    String notificationDetails;

    @Column(name = "notification_link")
    String notificationLink;

    @Column(name = "notification_category")
    String notificationCategory;

    @Column(name = "notification_created_by")
    String notificationCreatedBy;

    @Column(name = "notification_created_on")
    String notificationCreatedOn;

    @Column(name = "notification_source")
    String notificationSource;

    @Column(name = "viewed_by_through_app")
    String viewedByThroughApp;

    @Column(name = "dms_coll_id")
    String dmsCollId;
}