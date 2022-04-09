package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "project_master_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class ProjectMasterTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_code")
    String projectCode;

    @Column(name = "project_name")
    String projectName;

    @Column(name = "project_no")
    String projectNo;

    @Column(name = "client_name")
    String clientName;

    @Column(name = "client_version_no")
    String clientVersionNo;

    @Column(name = "project_type")
    String projectType;

    @Column(name = "proj_start_date")
    String projStartDate;

    @Column(name = "proj_end_date")
    String projEndDate;

    @Column(name = "billing_type")
    String billingType;

    @Column(name = "project_value")
    String projectValue;

    @Column(name = "po_received")
    String poReceived;

    @Column(name = "scope_define")
    String scopeDefine;

    @Column(name = "contractMou_received")
    String contractMouReceived;

    @Column(name = "payment_terms")
    String paymentTerms;

    @Column(name = "project_desc")
    String projectDesc;

    @Column(name = "proj_signed_of_obtained")
    String projSignedOfObtained;

    @Column(name = "doc_given")
    String docGiven;

    @Column(name = "training_OR_handover")
    String trainingORHandover;

    @Column(name = "time_variance")
    String timeVariance;

    @Column(name = "project_remarks")
    String projectRemarks;

    @Column(name = "file_attatch")
    String fileAttatch;

    @Column(name = "act_end_date")
    String actEndDate;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "project_duration")
    String projectDuration;

    @Column(name = "project_estimated_cost")
    String projectEstimatedCost;

    @Column(name = "project_budgethead_name")
    String projectBudgetheadName;

    @Column(name = "project_monitoring_authority")
    String projectMonitoringAuthority;

    @Column(name = "division_name")
    String divisionName;

    @Column(name = "tot_agree_amt")
    String totAgreeAmt;

    @Column(name = "tot_extra_item_amt")
    String totExtraItemAmt;

    @Column(name = "tot_admin_approve_amt")
    String totAdminApproveAmt;

    @Column(name = "tot_tech_sanction_amt")
    String totTechSanctionAmt;

    @Column(name = "tot_estimated_amt")
    String totEstimatedAmt;

    @Column(name = "tot_contract_amt")
    String totContractAmt;

    @Column(name = "total_bill_amt")
    String totalBillAmt;

    @Column(name = "total_paid_amt")
    String totalPaidAmt;

    @Column(name = "total_balance_amt")
    String totalBalanceAmt;

    @Column(name = "tot_penalty_amt")
    String totPenaltyAmt;

    @Column(name = "tot_tend_estimate_amt")
    String totTendEstimateAmt;

    @Column(name = "tot_tend_contract_amt")
    String totTendContractAmt;

    @Column(name = "grant_allocatet_amt")
    String grantAllocatetAmt;

    @Column(name = "grant_expenditure_amt")
    String grantExpenditureAmt;

    @Column(name = "grant_total_amt")
    String grantTotalAmt;

    @Column(name = "award_type")
    String awardType;

    @Column(name = "parent_id")
    String parentId;

    @Column(name = "all_parent_id")
    String allParentId;

    @Column(name = "all_project_name")
    String allProjectName;

    @Column(name = "project_category")
    String projectCategory;

    @Column(name = "grant_received_amt")
    String grantReceivedAmt;

    @Column(name = "main_project_code")
    String mainProjectCode;

    @Column(name = "temp_delete")
    String tempDelete;

    @Column(name = "allocate_resources")
    String allocateResources;

    @Column(name = "allocated_emp")
    String allocatedEmp;

    @Column(name = "client_nm")
    String clientNm;

    @Column(name = "division_nm")
    String divisionNm;

    @Column(name = "order_acceptance")
    String orderAcceptance;

    @Column(name = "customer_po")
    String customerPo;

    @Column(name = "project_close")
    String projectClose;
}