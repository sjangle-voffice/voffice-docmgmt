 package com.voffice.rearch.persistenance.repository;

 import com.voffice.rearch.persistenance.entity.MasterTagTbl;
 import com.voffice.rearch.persistenance.entity.dto.ActiveTagsDTO;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Repository;

 import java.util.List;

 @Repository
 public interface MasterTagsTblRepository extends CrudRepository<MasterTagTbl, String> {

     @Query( value = " SELECT new com.voffice.rearch.persistenance.entity.dto.ActiveTagsDTO " +
          " (tbl.tagId, tbl.tagName) FROM MasterTagTbl tbl Where tempDelete = 'NO' order by tagName ")
     List<ActiveTagsDTO> fetchAllMasterTags();
 }