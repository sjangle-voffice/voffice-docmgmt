package com.voffice.rearch.persistenance.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DmsCollaborationInputDTO {

    private String documentName;
    private String documentdesc;
    private String documentTags;
    private String documentType;
    private String projectName;
    private String userPreDefinedTagCheck;
    private boolean createDiscussionThread;
    private boolean shareDocWithAllUsers;
    private boolean shareDocWithSpecUsers;
    private String communityId;
    private String usersListToShareDocWith;

}