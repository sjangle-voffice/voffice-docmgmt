package com.voffice.rearch.persistenance.repository;

import com.voffice.rearch.persistenance.entity.DocumentTypeTbl;
import com.voffice.rearch.persistenance.entity.dto.DocumentTypeDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeTblRepository extends CrudRepository<DocumentTypeTbl, Integer> {

    @Query( value = " SELECT new com.voffice.rearch.persistenance.entity.dto.DocumentTypeDTO " +
            " (tbl.documentId, tbl.documentName) FROM DocumentTypeTbl tbl order by document_name ")
    List<DocumentTypeDTO> fetchAllDocumentTypes();
}

