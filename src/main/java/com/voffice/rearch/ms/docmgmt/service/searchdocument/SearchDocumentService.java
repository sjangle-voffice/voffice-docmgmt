package com.voffice.rearch.ms.docmgmt.service.searchdocument;

import com.voffice.rearch.persistenance.pojo.DocumentSearchRequestPojo;
import com.voffice.rearch.persistenance.pojo.DocumentSearchResultPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * This
 *
 * @author Sagar Jangle.
 * @version 1.0
 * @since 1st Feb 2022
 **/
@Service
public class SearchDocumentService {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private static final Logger LOG = Logger.getLogger(SearchDocumentService.class.getName());

    public List<DocumentSearchResultPojo> searchDocuments(DocumentSearchRequestPojo documentSearchRequestPojo) throws Exception {

        List<String> membersList = getListOfReportee(documentSearchRequestPojo.getUserAccessRoleId());
        System.out.println("membersList : " + membersList);

        List<DocumentSearchResultPojo> searchDocumentList = jdbcTemplate.query(
                " SELECT dmscoll.*,dms.*,emp.employee_name AS dmscollcreator,emp.employee_role ,emp.attachment , emp_1.employee_name AS lastcommentor,\n" +
                        " emp_2.employee_name AS doccreator , emp_3.employee_name AS doccheckouter, role.user_role," +
                        " IFNULL((SELECT GROUP_CONCAT(comm.`community_name`) FROM community_tbl AS comm WHERE FIND_IN_SET(comm.community_id,\n" +
                        " REPLACE(dmscoll.`community_id`,' ',''))ORDER BY comm.`community_name`),'')AS comm_names,   IFNULL((SELECT GROUP_CONCAT(emp.`employee_name`)\n" +
                        " FROM `employee_tbl`  AS emp WHERE FIND_IN_SET(emp.`employee_code`,REPLACE(dmscoll.`member_id`,' ','')) ORDER BY emp.`employee_name`),'')\n" +
                        " AS emp_names FROM dms_collaboration_tbl AS dmscoll LEFT JOIN dms_list_tbl AS dms  ON (dmscoll.dms_collaboration_id = dms.dms_collaboration_id \n" +
                        " AND  dms.`version` LIKE (SELECT MAX(`version`) FROM`dms_list_tbl` WHERE `parent_doc_id`=dms.`parent_doc_id` AND delete_status!='delete')\n" +
                        " AND delete_status!='delete' )  LEFT JOIN employee_tbl AS emp ON (dmscoll.created_by = emp.employee_code) LEFT JOIN employee_tbl AS emp_1 ON (dmscoll.last_comment_by = emp_1.employee_code) LEFT JOIN employee_tbl AS emp_2 ON (dms.created_by = emp_2.employee_code)\n" +
                        " LEFT JOIN employee_tbl AS emp_3 ON (dms.check_in_out_by = emp_3.employee_code)  LEFT JOIN master_user_role AS role ON (emp.`employee_role`=role.user_role_id)   LEFT JOIN `community_tbl` AS comm ON(dmscoll.`community_id`=comm.`community_id`) \n" +
                        " WHERE dmscoll.dms_collaboration_title LIKE ? \n" +
                        " AND dmscoll.document_type LIKE ? \n" +
                        " AND dmscoll.dms_collaboration_tags LIKE ? \n" +
                        " AND LOWER(CONCAT(IFNULL(dmscoll.dms_collaboration_title,''),  IFNULL(dms.document_text,''),  IFNULL(dmscoll.dms_collaboration_tags,'') )) LIKE LOWER( ? ) \n" +
                        " AND (dmscoll.dms_collaboration_access LIKE 'public' \n" +
                        " OR dmscoll.member_id LIKE ? \n" +
                        " OR IFNULL(dmscoll.created_by,'') LIKE ? \n" +
                        " AND ? IN(dmscoll.created_by) \n" +
                        " OR (dmscoll.created_by IN ( ? )) \n" +
                        " OR dmscoll.member_id IN ( ? ) ) \n" +
                        " AND STR_TO_DATE(dmscoll.created_on,'%d/%m/%Y') \n" +
                        " BETWEEN STR_TO_DATE( ? ,'%d/%m/%Y') \n" +
                        " AND STR_TO_DATE( ? ,'%d/%m/%Y') \n" +
                        " AND dmscoll.creation_source LIKE ? \n" +
                        " AND (dms.project_name LIKE ? OR  dmscoll.source_of_record LIKE ? ) \n" +
                        " GROUP BY dmscoll.dms_collaboration_id ORDER BY dmscoll.dms_collaboration_id DESC"
                    , new Object[] {
                        "%"  + documentSearchRequestPojo.getDocumentName() + "%",
                        "%"  + documentSearchRequestPojo.getDocumentType() + "%",
                        "%"  + documentSearchRequestPojo.getDocumentTag() + "%",
                        "%"  + documentSearchRequestPojo.getDmsKeywords() + "%",
                        "%"  + documentSearchRequestPojo.getEmployeeCode() + "%",
                        documentSearchRequestPojo.getEmployeeCode(),
                        documentSearchRequestPojo.getEmployeeCode(),
                        membersList,
                        membersList,
                        documentSearchRequestPojo.getFromDate(),
                        documentSearchRequestPojo.getToDate(),
                        "%"  + documentSearchRequestPojo.getDocumentSource() + "%",
                        "%"  + documentSearchRequestPojo.getProjectName() + "%",
                        "%" + documentSearchRequestPojo.getDocumentSource() + "%"
                    },
                    new RowMapper<DocumentSearchResultPojo>() {
                    @Override
                    public DocumentSearchResultPojo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        DocumentSearchResultPojo searchDocumentResultPojo = new DocumentSearchResultPojo();
                        searchDocumentResultPojo.setDmsCollaborationId(rs.getString("dms_collaboration_id"));
                        searchDocumentResultPojo.setDmsCollaborationType(rs.getString("dms_collaboration_type"));
                        searchDocumentResultPojo.setSourceOfRecord(rs.getString("source_of_record"));
                        searchDocumentResultPojo.setCommunityId(rs.getString("community_id"));
                        searchDocumentResultPojo.setMemberId(rs.getString("member_id"));
                        searchDocumentResultPojo.setDmsCollaborationTitle(rs.getString("dms_collaboration_title"));
                        searchDocumentResultPojo.setDescription(rs.getString("description"));
                        searchDocumentResultPojo.setDmsCollaborationTags(rs.getString("dms_collaboration_tags"));
                        searchDocumentResultPojo.setDmsCollaborationAccess(rs.getString("dms_collaboration_access"));
                        searchDocumentResultPojo.setCreatedBy(rs.getString("created_by"));
                        searchDocumentResultPojo.setCreatedOn(rs.getString("created_on"));
                        searchDocumentResultPojo.setTotalShareMemberCount(rs.getString("total_share_member_count"));
                        searchDocumentResultPojo.setTotalComment(rs.getString("total_comment"));
                        searchDocumentResultPojo.setTotalLikes(rs.getString("total_likes"));
                        searchDocumentResultPojo.setLastComment(rs.getString("last_comment"));
                        searchDocumentResultPojo.setLastCommentBy(rs.getString("last_comment_by"));
                        searchDocumentResultPojo.setLastCommentOn(rs.getString("last_comment_on"));
                        searchDocumentResultPojo.setDiscussionCreated(rs.getString("discussion_created"));
                        searchDocumentResultPojo.setDiscussionStatus(rs.getString("discussion_status"));
                        searchDocumentResultPojo.setCreationSource(rs.getString("creation_source"));
                        searchDocumentResultPojo.setDocumentType(rs.getString("document_type"));
                        searchDocumentResultPojo.setInwardOutwardDate(rs.getString("inward_outward_date"));
                        searchDocumentResultPojo.setInwardFromOutwardTo(rs.getString("inwardfrom_outwardto"));
                        searchDocumentResultPojo.setInwardToOutwatdFrom(rs.getString("inwardto_outwatdfrom"));
                        searchDocumentResultPojo.setIsProjectRelated(rs.getString("is_project_related"));
                        searchDocumentResultPojo.setProjectName(rs.getString("project_name"));
                        searchDocumentResultPojo.setDepartmentName(rs.getString("department_name"));
                        searchDocumentResultPojo.setRemark(rs.getString("remark"));
                        searchDocumentResultPojo.setSecretarialFlag(rs.getString("secretarial_tag"));
                        searchDocumentResultPojo.setIsViewed(rs.getString("isviewed"));
                        searchDocumentResultPojo.setDmsDeleteStatus(rs.getString("dms_delete_status"));
                        searchDocumentResultPojo.setSubCreationSource(rs.getString("sub_creation_source"));
                        searchDocumentResultPojo.setCompetitorName(rs.getString("competitor_name"));
                        searchDocumentResultPojo.setCustomerThrough(rs.getString("customer_through"));
                        searchDocumentResultPojo.setDistributor(rs.getString("distributor"));
                        searchDocumentResultPojo.setSubDistributor(rs.getString("sub_distributor"));
                        searchDocumentResultPojo.setCustomerName(rs.getString("customer_name"));
                        searchDocumentResultPojo.setSubCustomerName(rs.getString("sub_customer_name"));
                        searchDocumentResultPojo.setPlant(rs.getString("plant"));
                        searchDocumentResultPojo.setDocRelated(rs.getString("doc_related"));
                        searchDocumentResultPojo.setDocumentId(rs.getString("document_id"));
                        searchDocumentResultPojo.setParentDocId(rs.getString("parent_doc_id"));
                        searchDocumentResultPojo.setDmsCollaborationId(rs.getString("dms_collaboration_id"));
                        searchDocumentResultPojo.setSourceOfRecord(rs.getString("source_of_record"));
                        searchDocumentResultPojo.setSourceOfRecordVersion(rs.getString("source_of_record_version"));
                        searchDocumentResultPojo.setOriginalName(rs.getString("original_name"));
                        searchDocumentResultPojo.setFilePath(rs.getString("file_path"));
                        searchDocumentResultPojo.setVersion(rs.getString("version"));
                        searchDocumentResultPojo.setFinalVersion(rs.getString("final_version"));
                        searchDocumentResultPojo.setCheckInOutBy(rs.getString("check_in_out_by"));
                        searchDocumentResultPojo.setCheckInOutOn(rs.getString("check_in_out_on"));
                        searchDocumentResultPojo.setCreatedBy(rs.getString("created_by"));
                        searchDocumentResultPojo.setCreatedOn(rs.getString("created_on"));
                        searchDocumentResultPojo.setDeleteStatus(rs.getString("delete_status"));
                        searchDocumentResultPojo.setSourceOfCreation(rs.getString("sourceof_creation"));
                        searchDocumentResultPojo.setDocumentText(rs.getString("document_text"));
                        searchDocumentResultPojo.setMarkTo(rs.getString("mark_to"));
                        searchDocumentResultPojo.setMarkingRemark(rs.getString("marking_remark"));
                        searchDocumentResultPojo.setProjectName(rs.getString("project_name"));
                        searchDocumentResultPojo.setDepartmentName(rs.getString("department_name"));
                        searchDocumentResultPojo.setMarkingDate(rs.getString("marking_date"));
                        searchDocumentResultPojo.setMarkedBy(rs.getString("marked_by"));
                        searchDocumentResultPojo.setMarkFlag(rs.getString("mark_flag"));
                        searchDocumentResultPojo.setProjectDetailsAttach(rs.getString("project_details_attach"));
                        searchDocumentResultPojo.setDmscollcreator(rs.getString("dmscollcreator"));
                        searchDocumentResultPojo.setEmployeeRole(rs.getString("employee_role"));
                        searchDocumentResultPojo.setAttachment(rs.getString("attachment"));
                        searchDocumentResultPojo.setLastcommentor(rs.getString("lastcommentor"));
                        searchDocumentResultPojo.setDoccreator(rs.getString("doccreator"));
                        searchDocumentResultPojo.setDoccheckouter(rs.getString("doccheckouter"));
                        searchDocumentResultPojo.setUserRole(rs.getString("user_role"));
                        searchDocumentResultPojo.setCommNames(rs.getString("comm_names"));
                        searchDocumentResultPojo.setEmpNames(rs.getString("emp_names"));
                        return searchDocumentResultPojo;
                    }
                });

        return searchDocumentList;
    }

    private List<String> getListOfReportee(String userAccessRoleId) {
        List<String> retMembersList = new ArrayList<>();
        retMembersList.add(userAccessRoleId);

        List<String> membersList = getMembersListForId(userAccessRoleId);

        StringTokenizer tokenizer = new StringTokenizer(membersList.get(0), ",");
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        if (tokenizer.countTokens() == 1 ) {
            return membersList;
        } else {
            retMembersList.addAll(getRecursiveList(tokens));
            return retMembersList;
        }
    }

    private List<String> getRecursiveList(List<String> tokens) {
        List<String> retList = new ArrayList<>();

        List<String> tempList = null;
        if ( tokens.size() == 1) {
            return tokens;
        } else {
            for (String token : tokens) {
                tempList = getMembersListForId(token);
                if (tempList.size() == 0) {
                    retList.add(token);
                } else {
                    retList.addAll(getRecursiveList(tempList));
                }
            }
        }
        return retList;
    }

    private List<String> getMembersListForId(String memberId) {

        List<String> membersListForId = jdbcTemplate.query (
                "select report_to_level from master_user_role where user_access_role_id = ? ",
                new Object[] { memberId },
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("report_to_level");
                    }
                });
        return membersListForId;
    }
}