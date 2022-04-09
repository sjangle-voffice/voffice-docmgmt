package com.voffice.rearch.ms.docmgmt.controller.searchdocument;

import com.voffice.rearch.ms.docmgmt.service.searchdocument.SearchDocumentService;
import com.voffice.rearch.persistenance.cache.SearchDocDataCache;
import com.voffice.rearch.persistenance.entity.DmsListTbl;
import com.voffice.rearch.persistenance.entity.dto.DeleteDocSpecVersionDTO;
import com.voffice.rearch.persistenance.pojo.DocumentSearchRequestPojo;
import com.voffice.rearch.persistenance.pojo.DocumentSearchResultPojo;
import com.voffice.rearch.persistenance.repository.DmsListTblRepository;
import com.voffice.rearch.utils.exception.model.BadResourceException;
import com.voffice.rearch.utils.exception.model.ResourceNotFoundException;
import com.voffice.rearch.utils.misc.ApplPageEnum;
import com.voffice.rearch.utils.misc.RecordStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * This search controller will be used for search documents page. This controller supports below methods :
 * - initializeCache() - This method will create needed Cache for this page.
 * - searchDocuments() - This method will bring the search results as per search criteria from DB.
 * - getSrcDocMetadata() - This method will create search page metadata needed to during page load.
 * - deleteDocument() - This method will delete document along with all versions.
 * - deleteSpecificVersionDocument() - This method will delete sepcific version of the document.
 *
 * @author Sagar Jangle.
 * @version 1.0
 * @since 1st Feb 2022
 **/
@CrossOrigin
@RestController
public class SearchDocumentController {

    private static Logger log = LoggerFactory.getLogger(SearchDocumentController.class);

    @Autowired
    private SearchDocDataCache searchDocDataCache;

    @Autowired
    private DmsListTblRepository dmsListTblRepository;

    @Autowired
    private SearchDocumentService searchDocumentService;

    @RequestMapping(
            value="/searchDocuments",
            headers="Accept=application/json",
            method=RequestMethod.POST)
    public ResponseEntity<List<DocumentSearchResultPojo>> searchDocuments (
            @RequestBody DocumentSearchRequestPojo documentSearchRequestPojo) throws Exception {

        log.info("searchDocuments Method Called ");

// Tested with below sample Input JSON :

//        {
//            "documentName": "",
//                "documentType": "",
//                "documentTag": "",
//                "fromDate": "01/03/2021",
//                "toDate": "31/03/2022",
//                "documentSource": "",
//                "resourceName": "",
//                "employeeCode": "emp_1_v",
//                "communityDivison": "",
//                "dmsKeywords": "",
//                "projectName": "",
//                "designation": "",
//                "filter": "",
//                "userAccessRoleId": "r_1_v"
//        }

        return new ResponseEntity<>(
                searchDocumentService.searchDocuments(documentSearchRequestPojo), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getSrcDocMetadata")
    @PreAuthorize("hasAuthority('ROLE_HOME_STEP1LINK')")
    public ResponseEntity<SearchDocDataCache.SearchDocCache> getSrcDocMetadata () throws Exception {
        log.info("getSrcDocMetadata API Called");
        return new ResponseEntity<SearchDocDataCache.SearchDocCache>(
                searchDocDataCache.getSearchDataCache(ApplPageEnum.SRCH_DOC.ordinal()).get(),
                HttpStatus.OK);
    }

    @PatchMapping("/deleteDocument/{documentId}")
    @PreAuthorize("hasAuthority('ROLE_HOME_STEP1LINK')")
    public ResponseEntity<Void> deleteDocument(@PathVariable int documentId) {
        log.info("deleteDocument API Called");
        try {
            Optional<DmsListTbl> dmsListTbl = dmsListTblRepository.getLatestVersionDocument(documentId);
            DmsListTbl docToDelete = dmsListTbl.get();
            docToDelete.setDeleteStatus(String.valueOf(RecordStatusEnum.REC_DELETE_STATUS));
            dmsListTblRepository.save(docToDelete);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            log.error("ResourceNotFoundException during deleteDocument : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            log.error("BadResourceException during deleteDocument : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/deleteSpecVersionDocument/{documentId}")
    @PreAuthorize("hasAuthority('ROLE_HOME_STEP1LINK')")
    public ResponseEntity<Void> deleteSpecificVersionDocument(
            @PathVariable("documentId") int documentId,
            @RequestBody DeleteDocSpecVersionDTO deleteDocSpecVersionDTO) {

        log.info("Delete Specific Version Document API Called");
        try {
            Optional<DmsListTbl> dmsListTbl =
                    dmsListTblRepository.getVersionSpecificDocument(documentId, deleteDocSpecVersionDTO.getDocVersion());
            DmsListTbl docToDelete = dmsListTbl.get();
            docToDelete.setDeleteStatus(String.valueOf(RecordStatusEnum.REC_DELETE_STATUS));
            dmsListTblRepository.save(docToDelete);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            log.error("ResourceNotFoundException during deleteDocument : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            log.error("BadResourceException during deleteDocument : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostConstruct
    public void initializeCache () throws Exception {
        log.info("Cache initializeCache started for page : Search Document ");
        searchDocDataCache.initializeSearchDocCache(ApplPageEnum.SRCH_DOC.ordinal());
        log.info("Cache initializeCache completed for page : Search Document ");
    }
}