package com.voffice.rearch.persistenance.repository;

import com.voffice.rearch.persistenance.entity.EmployeeTbl;
import com.voffice.rearch.persistenance.entity.dto.ActiveEmpDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTblRepository extends CrudRepository<EmployeeTbl, String> {

    @Query( value = " SELECT new com.voffice.rearch.persistenance.entity.dto.ActiveEmpDTO " +
            " (tbl.employeeCode, tbl.employeeName) FROM EmployeeTbl tbl where accountstatus = 'Active' order by employee_name ")
    List<ActiveEmpDTO> findByActiveStatusEmployees();
}