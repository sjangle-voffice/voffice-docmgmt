package com.voffice.rearch.persistenance.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSearchRequestPojo {

    private String documentName;
	private String documentType;
	private String documentTag;
	private String fromDate;
	private String toDate;
	private String documentSource;
	private String resourceName;
	private String employeeCode;
	private String communityDivison;
	private String dmsKeywords;
	private String projectName;
	private String designation;
	private String filter;
	private String userAccessRoleId;
}