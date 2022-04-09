package com.voffice.rearch.persistenance.repository;

import com.voffice.rearch.persistenance.entity.DmsCollaborationTbl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmsCollaborationTblRepository extends CrudRepository<DmsCollaborationTbl, Integer> {
}