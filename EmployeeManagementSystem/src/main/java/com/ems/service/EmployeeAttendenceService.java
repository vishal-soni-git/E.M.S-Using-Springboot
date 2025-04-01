package com.ems.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entities.EmployeeAttendence;
import com.ems.repository.EmployeeAttendenceRepository;

@Service
public class EmployeeAttendenceService {

    @Autowired
    EmployeeAttendenceRepository employeeAttendenceRepository;

    public void saveEmployeeAttendence(EmployeeAttendence employeeAttendece){

        employeeAttendenceRepository.save(employeeAttendece);

    }

     public boolean hasMarkedAttendanceToday(Integer empId,LocalDate date) {
        return employeeAttendenceRepository.existsByEmpIdAndDate(empId, date);
    }

}
