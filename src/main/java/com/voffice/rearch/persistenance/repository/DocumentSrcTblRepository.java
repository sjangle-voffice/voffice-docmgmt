package com.voffice.rearch.persistenance.repository;

import com.voffice.rearch.persistenance.entity.DocumentSourceTbl;
import com.voffice.rearch.persistenance.entity.dto.DocumentSrcDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentSrcTblRepository extends CrudRepository<DocumentSourceTbl, Integer> {

    @Query( value = " SELECT new com.voffice.rearch.persistenance.entity.dto.DocumentSrcDTO " +
            " (tbl.docDescription, tbl.docUiDesc) FROM DocumentSourceTbl tbl order by doc_description ")
    List<DocumentSrcDTO> fetchAllDocumentSources();
}




