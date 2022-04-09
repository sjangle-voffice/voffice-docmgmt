 package com.voffice.rearch.persistenance.repository;

 import com.voffice.rearch.persistenance.entity.EmployeeCommunityMapTbl;
 import com.voffice.rearch.persistenance.entity.dto.EmpCommunityDTO;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Repository;

 import java.util.List;

 @Repository
 public interface EmpCommunityMapTblRepository extends CrudRepository<EmployeeCommunityMapTbl, Integer> {

     @Query( value = " SELECT new com.voffice.rearch.persistenance.entity.dto.EmpCommunityDTO " +
             " (tbl.communityId, tbl.memberId) FROM EmployeeCommunityMapTbl tbl order by communityId ")
     List<EmpCommunityDTO> fetchAllEmpCommunitiesMappings();
 }





