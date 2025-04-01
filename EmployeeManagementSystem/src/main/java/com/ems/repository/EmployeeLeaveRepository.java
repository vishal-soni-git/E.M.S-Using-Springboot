package com.ems.repository;
import com.ems.entities.EmployeeLeave;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave , Integer> {

    List<EmployeeLeave> findByEmployeeId(int employeeId);

    List<EmployeeLeave> findAllByOrderByIdDesc();

    List<EmployeeLeave> findByEmployeeIdOrderByIdDesc(int employeeId);

}
