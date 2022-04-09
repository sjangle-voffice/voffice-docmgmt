package com.voffice.rearch.persistenance.repository;

import com.voffice.rearch.persistenance.entity.NotificationsTbl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTblRepository extends CrudRepository<NotificationsTbl, Integer> {

}