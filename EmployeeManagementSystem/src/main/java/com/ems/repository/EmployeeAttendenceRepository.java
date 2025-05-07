package com.ems.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ems.entities.EmployeeAttendence;

@Repository
public interface EmployeeAttendenceRepository extends JpaRepository<EmployeeAttendence,Long> {

     @Query("SELECT COUNT(e) > 0 FROM EmployeeAttendence e WHERE e.empId = :empId AND e.date = :date")
    boolean existsByEmpIdAndDate(@Param("empId") Integer empId, @Param("date") LocalDate date);

     List<EmployeeAttendence> findByDate(LocalDate date);

     EmployeeAttendence findByEmpIdAndDate(int empId, LocalDate date);
      

}
