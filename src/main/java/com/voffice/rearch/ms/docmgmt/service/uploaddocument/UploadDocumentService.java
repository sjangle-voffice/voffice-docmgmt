package com.voffice.rearch.ms.docmgmt.service.uploaddocument;

import com.voffice.rearch.persistenance.entity.DmsCollaborationTbl;
import com.voffice.rearch.persistenance.entity.DmsListTbl;
import com.voffice.rearch.persistenance.entity.NotificationsTbl;
import com.voffice.rearch.persistenance.entity.dto.DmsCollaborationInputDTO;
import com.voffice.rearch.persistenance.repository.DmsCollaborationTblRepository;
import com.voffice.rearch.persistenance.repository.DmsListTblRepository;
import com.voffice.rearch.persistenance.repository.NotificationTblRepository;
import com.voffice.rearch.utils.common.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * TODO Pending
 * This
 *
 * @author Sagar Jangle.
 * @version 1.0
 * @since 1st Feb 2022
 **/
@Service
public class UploadDocumentService {

    private static final Logger LOG = Logger.getLogger(UploadDocumentService.class.getName());

    @Autowired
    private DmsCollaborationTblRepository dmsCollaborationTblRepository;

    @Autowired
    private DmsListTblRepository dmsListTblRepository;

    @Autowired
    private NotificationTblRepository notificationTblRepository;

    @Value("${local.storage.path}")
    private String localStoragePath;

    public DmsCollaborationTbl updateDmsColTable (DmsCollaborationInputDTO dmsCollaborationInputDTO, String loggedInUserId) {

        // TODO Need to use BeanUtils.copyProperties()
        DmsCollaborationTbl dmsCollaborationInpTbl = new DmsCollaborationTbl();

        dmsCollaborationInpTbl.setDmsCollaborationTitle(dmsCollaborationInputDTO.getDocumentName());
        dmsCollaborationInpTbl.setDescription(dmsCollaborationInputDTO.getDocumentdesc());
        dmsCollaborationInpTbl.setDmsCollaborationTags(dmsCollaborationInputDTO.getDocumentTags());
        dmsCollaborationInpTbl.setDocumentType(dmsCollaborationInputDTO.getDocumentType());
        dmsCollaborationInpTbl.setDmsDeleteStatus("no");

        // This should be Logged in User Id. We need to retrieve this UserId from Gateway.
        dmsCollaborationInpTbl.setCreatedBy(loggedInUserId);
        dmsCollaborationInpTbl.setCreatedOn(String.valueOf(new Date()));

        if ( Boolean.valueOf(dmsCollaborationInputDTO.isCreateDiscussionThread())) {
            dmsCollaborationInpTbl.setDmsCollaborationType("Thread");
            dmsCollaborationInpTbl.setDiscussionCreated("true");
            dmsCollaborationInpTbl.setCreationSource("Thread");
        } else {
            dmsCollaborationInpTbl.setDmsCollaborationType("Document");
            dmsCollaborationInpTbl.setCreationSource("Document");
        }

        if ( Boolean.valueOf(dmsCollaborationInputDTO.isShareDocWithSpecUsers())) {
            dmsCollaborationInputDTO.setCommunityId(dmsCollaborationInputDTO.getCommunityId());
            dmsCollaborationInputDTO.setUsersListToShareDocWith(dmsCollaborationInputDTO.getUsersListToShareDocWith());
            dmsCollaborationInpTbl.setDmsCollaborationAccess("custom");
            dmsCollaborationInpTbl.setTotalShareMemberCount(String.valueOf(
                    new StringTokenizer(dmsCollaborationInputDTO.getUsersListToShareDocWith(),",").countTokens()));
        } else {
            dmsCollaborationInpTbl.setDmsCollaborationAccess("public");
        }

        DmsCollaborationTbl dmsCollaborationTblNew = dmsCollaborationTblRepository.save(dmsCollaborationInpTbl);
        LOG.fine("Record added in DmsCollaborationTbl " + dmsCollaborationTblNew.getDmsCollaborationId());

        return dmsCollaborationTblNew;
    }

    public void insertNotificationsTable(String loggedInUserId, DmsCollaborationTbl dmsColTblRec) {

        NotificationsTbl notificationsTbl = new NotificationsTbl();
        notificationsTbl.setNotificationTo("ALL");
        notificationsTbl.setCommunityId(dmsColTblRec.getCommunityId());
        notificationsTbl.setNotificationViewed(null);

        String notificationMsg = loggedInUserId + " has shared " + dmsColTblRec.getDmsCollaborationTitle();
        notificationsTbl.setNotificationDetails(notificationMsg);

        // TODO Below value needs to generate at run time. Hard coded as of now.
        notificationsTbl.setNotificationLink("/voffice/dmscollaboration/gotoDiscussion?dmscollDomain.dmscollaboration_id=31&19");

        notificationsTbl.setNotificationCategory("Document");
        notificationsTbl.setNotificationCreatedBy(loggedInUserId);
        notificationsTbl.setNotificationCreatedOn(String.valueOf(new Date()));
        notificationsTbl.setNotificationSource(null);
        notificationsTbl.setViewedByThroughApp(null);
        notificationsTbl.setDmsCollId(String.valueOf(dmsColTblRec.getDmsCollaborationId()));

        notificationTblRepository.save(notificationsTbl);
    }

    public void updateDmsListTable (List<String> fileNames, String localStoragePath, DmsCollaborationTbl dmsColTblRec) {

        for (String fileName : fileNames) {
            DmsListTbl dmsListTbl = new DmsListTbl();
            dmsListTbl.setDmsCollaborationId(String.valueOf(dmsColTblRec.getDmsCollaborationId()));
            dmsListTbl.setParentDocId(String.valueOf(dmsColTblRec.getDmsCollaborationId()));
            dmsListTbl.setFilePath(localStoragePath + File.separator + fileName);
            dmsListTbl.setVersion("1");
            dmsListTbl.setFinalVersion("Y");
            dmsListTbl.setCreatedBy(dmsColTblRec.getCreatedBy());
            dmsListTbl.setCreatedOn(dmsColTblRec.getCreatedOn());
            dmsListTbl.setDeleteStatus(dmsColTblRec.getDmsDeleteStatus());
            dmsListTbl.setSourceOfCreation(dmsColTblRec.getCreationSource());

            if (FileUtils.isImgFile(new File(localStoragePath + File.separator + fileName)) ) {
                /** Set the Image name and other attributes to store as document text **/
                dmsListTbl.setDocumentText(dmsColTblRec.getDmsCollaborationTitle() + dmsColTblRec.getDmsCollaborationTags() + dmsColTblRec.getCreatedOn());

            } else {
                /** Set the document text of the file **/
                String fileContent = null;
                try {
                    fileContent = getFileContent(
                            new File(localStoragePath + File.separator + fileName));

                } catch (Exception exp) {
                    exp.printStackTrace();
                }
                dmsListTbl.setDocumentText(
                        dmsColTblRec.getDmsCollaborationTitle()
                                + dmsColTblRec.getDmsCollaborationTags()
                                + dmsColTblRec.getCreatedOn()
                                + fileContent);
            }

            DmsListTbl dmsListTbl1 = dmsListTblRepository.save(dmsListTbl);
            LOG.fine("Record added in Dms List Tbl " + dmsListTbl1.getDmsCollaborationId());
        }
    }

    private String getFileContent(File inputFile) throws Exception {
        String fileContents = null;

        Path filePathToProbe = Paths.get(localStoragePath + File.separator + inputFile.getName());
        System.out.println("filePathToProbe :" + filePathToProbe);

        String fileType = null;
        try {
            fileType = Files.probeContentType(filePathToProbe);
            System.out.println("fileType : " + fileType);

        } catch (IOException ioException) {
            System.out.println("ERROR: Unable to determine file type for "
                    + inputFile.getName()
                    + " due to exception "
                    + ioException);
        }

        switch (fileType) {
            case "text/plain" : {
                fileContents = FileUtils.getFileContents(inputFile).toString();
            }
            break;
            case "application/pdf" : {
                System.out.println("application/pdf");
                PDDocument document = PDDocument.load(inputFile);
                PDFTextStripper pdfStripper = new PDFTextStripper();
                fileContents = pdfStripper.getText(document);
            }
            break;
            case "application/msword" :
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" : {
                System.out.println("application/msword");

                try (XWPFDocument doc = new XWPFDocument(
                        Files.newInputStream(Paths.get(inputFile.getAbsolutePath())))) {

                    XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
                    fileContents = xwpfWordExtractor.getText();
                }
            }
            break;
            default:
                System.out.println("Unable to determine the file type. ");
                break;
        }
        System.out.println("fileContents --> : " + fileContents);
        return fileContents;
    }
}