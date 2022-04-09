package com.voffice.rearch.persistenance.repository;

import com.voffice.rearch.persistenance.entity.ProjectMasterTbl;
import com.voffice.rearch.persistenance.entity.dto.ProjectMasterDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMasterTblRepository extends CrudRepository<ProjectMasterTbl, String> {

    @Query( value = " SELECT new com.voffice.rearch.persistenance.entity.dto.ProjectMasterDTO " +
            " (tbl.projectCode, tbl.projectName) FROM ProjectMasterTbl tbl ")
    List<ProjectMasterDTO> fetchAllActiveProjects();
}