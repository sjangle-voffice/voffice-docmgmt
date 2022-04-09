package com.voffice.rearch.ms.docmgmt.controller.uploaddocument;

import com.voffice.rearch.persistenance.cache.UploadDocDataCache;
import com.voffice.rearch.persistenance.entity.DmsCollaborationTbl;
import com.voffice.rearch.persistenance.entity.dto.DmsCollaborationInputDTO;
import com.voffice.rearch.utils.misc.ApplPageEnum;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;

/**
 * This upload document controller will be used for upload documents page. This controller supports below methods :
 * - initializeCache() - This method will create needed Cache for this page.
 * - fileUpload() - This will actually perform the file upload function. This will internally create
 *   Temp folder needed to store the uploaded documents.
 *
 * @author Sagar Jangle.
 * @version 1.0
 * @since 1st Feb 2022
 **/
@CrossOrigin
@RestController
public class UploadDocumentController {

    private static Logger log = LoggerFactory.getLogger(UploadDocumentController.class);

    @Autowired
    private UploadDocDataCache uploadDocDataCache;

    @Autowired
    private com.voffice.rearch.ms.docmgmt.service.uploaddocument.UploadDocumentService uploadDocumentService;

    @Value("${local.storage.path}")
    private String localStoragePath;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_HOME_STEP1LINK')")
    public String fileUpload(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("documentName") String documentName,
            @RequestParam("documentdesc") String documentdesc,
            @RequestParam("documentTags") String documentTags,
            @RequestParam("documentType") String documentType,
            @RequestParam("projectName") String projectName,
            @RequestParam("userPreDefinedTags") String userPreDefinedTags,
            @RequestParam("createDiscussionThread") boolean createDiscussionThread,
            @RequestParam("shareDocWithAllUsers") boolean shareDocWithAllUsers,
            @RequestParam("shareDocWithSpecUsers") boolean shareDocWithSpecUsers,
            @RequestParam("usersListToShareDocWith") String usersListToShareDocWith,
            @RequestParam("communityId") String communityId,
            @RequestHeader("X-LoggedIn-UserId") String loggedInUserId) throws IOException {

        log.info("File upload API called with files : " + files);
        log.info("X-LoggedIn-UserId : " + loggedInUserId);

        if (files.length == 0 ) {
            return "Please select a file / files to upload";
        }

        List<String> fileNames = new ArrayList<>();
        try {
            Path localStoragePathToCreate = Paths.get(localStoragePath + File.separator);
            Arrays.asList(files).stream().forEach(file -> {
                try {
                    Files.copy(file.getInputStream(),
                            localStoragePathToCreate.resolve(file.getOriginalFilename()));
                } catch (Exception e) {
                    log.error("Could not store the file. Error: " + e.getMessage());
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
                fileNames.add(file.getOriginalFilename());
            });
        } catch (Exception exp) {
            log.error(ExceptionUtils.getStackTrace(exp));
            log.error("Exception while copying the file to " + localStoragePath + " location");
        }

        log.info("Uploaded files are :" + fileNames);

        /** Update in the DMS Collaboration Tbl **/
        DmsCollaborationTbl dmsCollaborationTblNew =
                uploadDocumentService.updateDmsColTable(
                        new DmsCollaborationInputDTO(
                                documentName, documentdesc, documentTags, documentType, projectName,
                                userPreDefinedTags, createDiscussionThread, shareDocWithAllUsers,
                                shareDocWithSpecUsers, communityId, usersListToShareDocWith), loggedInUserId);

        /** Update in the DMS List Tbl **/
        uploadDocumentService.updateDmsListTable(fileNames, localStoragePath, dmsCollaborationTblNew);

        /** Update in the Notification Tbl. **/
        uploadDocumentService.insertNotificationsTable(loggedInUserId, dmsCollaborationTblNew);

        return "Files uploaded are : " + fileNames;
    }

    @PostConstruct
    public void initializeCache () throws Exception {
        log.info("initializeCache cache needed for the Upload Document Page : Started ");
        uploadDocDataCache.initializeUploadDocCache(ApplPageEnum.UPLD_DOC.ordinal());
        log.info("initializeCache cache needed for the Upload Document Page : Completed ");
    }

    @PostConstruct
    public void createFolderForDocUpload () throws Exception {
        log.info("Creating Temp Folder for Doc Upload : Started ");

        try {
            Path localStoragePathToCreate = Paths.get(localStoragePath+ File.separator);

            String osName = System.getProperty("os.name").toLowerCase();
            if (!osName.contains("windows")) {
                Set<PosixFilePermission> perms = EnumSet.of(PosixFilePermission.OWNER_READ,
                        PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE,
                        PosixFilePermission.GROUP_READ, PosixFilePermission.GROUP_WRITE,
                        PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_READ,
                        PosixFilePermission.OTHERS_WRITE, PosixFilePermission.OTHERS_EXECUTE);

                Files.createDirectories(localStoragePathToCreate, PosixFilePermissions.asFileAttribute(perms));
            } else {
                Files.createDirectories(localStoragePathToCreate);
            }
            log.info("Directory created at : path -> " + localStoragePathToCreate);

        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            log.error("Exception while creating the TEMP folder : " + localStoragePath);
            throw new RuntimeException("Could not initialize folder for upload!");
        }

        log.info("Creating Temp Folder for Doc Upload : Completed ");
    }
}