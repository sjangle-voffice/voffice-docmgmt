package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employee_community_map_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeCommunityMapTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    int mapId;

    @Column(name = "community_id")
    int communityId;

    @Column(name = "member_id")
    String memberId;

    @Column(name = "role_in_dept")
    String roleInDept;
}