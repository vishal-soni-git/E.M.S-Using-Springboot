package com.ems.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ems.entities.Employee;
import com.ems.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public void saveEmployee(Employee e){
        System.out.println("Saving Employee Data");
        employeeRepository.save(e);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    public Optional<Employee> findById(Integer employeeId) {
       return employeeRepository.findById(employeeId);
    }

    public boolean isEmployeeExist(int id){

        return employeeRepository.existsById(id);
    }

    public boolean deleteEmployee(int id){
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        else{
        return false;
        }
    }

    public String convertIntoBase64Photo(byte[] photo){

        return photo != null ? Base64.getEncoder().encodeToString(photo) : null;
    }

}
