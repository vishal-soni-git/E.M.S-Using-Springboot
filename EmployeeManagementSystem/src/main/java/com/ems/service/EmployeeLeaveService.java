package com.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entities.Employee;
import com.ems.entities.EmployeeLeave;
import com.ems.repository.EmployeeLeaveRepository;

@Service
public class EmployeeLeaveService {
    
    @Autowired
    EmployeeLeaveRepository employeeLeaveRepository;


    public void save(EmployeeLeave el){
        employeeLeaveRepository.save(el);
    }

    public List<EmployeeLeave> getAllEmployees() {
        return employeeLeaveRepository.findAll();
    }

    public EmployeeLeave getEmployeeById(int id) {
        return employeeLeaveRepository.findById(id).orElse(null);
    }

    public List<EmployeeLeave> getByEmployee_Id(int employee_Id) {
        return employeeLeaveRepository.findByEmployeeId(employee_Id);
    }

    public List<EmployeeLeave> getByEmployeeIdSortedDesc(int employeeId) {
        return employeeLeaveRepository.findByEmployeeIdOrderByIdDesc(employeeId);
    }

    public List<EmployeeLeave> getAllByIdDesc() {
        return employeeLeaveRepository.findAllByOrderByIdDesc();
    }
}

