 package com.voffice.rearch.persistenance.repository;

 import com.voffice.rearch.persistenance.entity.CommunityTbl;
 import com.voffice.rearch.persistenance.entity.dto.CommunityDTO;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Repository;

 import java.util.List;

 @Repository
 public interface CommunityMasterTblRepository extends CrudRepository<CommunityTbl, Integer> {

     @Query( value = " SELECT new com.voffice.rearch.persistenance.entity.dto.CommunityDTO " +
             " (tbl.communityId, tbl.communityName) FROM CommunityTbl tbl order by communityName ")
     List<CommunityDTO> fetchAllCommunities();
 }