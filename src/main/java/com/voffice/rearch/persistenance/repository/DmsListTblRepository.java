package com.voffice.rearch.persistenance.repository;

import com.voffice.rearch.persistenance.entity.DmsListTbl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DmsListTblRepository extends CrudRepository<DmsListTbl, Integer> {

    @Query(value = "SELECT dmsListTbl.* FROM unity_ol.dms_list_tbl dmsListTbl " +
            " INNER JOIN\n" +
            " (SELECT document_id, version FROM unity_ol.dms_list_tbl\n" +
            " GROUP BY document_id) groupedDocId\n" +
            " ON dmsListTbl.document_id = groupedDocId.document_id\n" +
            " and dmsListTbl.document_id = ?1 ", nativeQuery = true)
    Optional<DmsListTbl> getLatestVersionDocument(int documentId);

    @Query(value = "SELECT dmsListTbl.* FROM unity_ol.dms_list_tbl dmsListTbl " +
            " INNER JOIN\n" +
            " (SELECT document_id, version FROM unity_ol.dms_list_tbl\n" +
            " GROUP BY document_id) groupedDocId\n" +
            " ON dmsListTbl.document_id = groupedDocId.document_id\n" +
            " and dmsListTbl.document_id = ?1 and dmsListTbl.version = ?2", nativeQuery = true)
    Optional<DmsListTbl> getVersionSpecificDocument(int documentId, int docVersion);
}