package com.ems.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public List<EmployeeAttendence> getAttendaceByDate(LocalDate date){

        return employeeAttendenceRepository.findByDate(date);
    }

    public Optional<EmployeeAttendence> getByID(long id){
        return employeeAttendenceRepository.findById(id);
    }


     public EmployeeAttendence getByEmployeeIdAndDate(int empId, LocalDate date){
        return employeeAttendenceRepository.findByEmpIdAndDate(empId, date);
     }
    
    

     public void markOrUnmarkAttendence(String action,String id){
        //  EmployeeAttendence employeeAttendence= employeeAttendenceService.getByEmployeeIdAndDate(Integer.parseInt(empId), LocalDate.now());

        Optional<EmployeeAttendence> employeeAttendenceOptional=getByID(Long.parseLong(id));

        EmployeeAttendence employeeAttendence=employeeAttendenceOptional.get();

        if(action.equals("accept"))
        {
           employeeAttendence.setStatus("PRESENT");
            System.out.println("Accept leave");
        }
        else if (action.equals("reject")) {
           employeeAttendence.setStatus("ABSENT");
            System.out.println("Reject Leaves");
        }
        else{
            System.out.println("some error action not found");
        }

       saveEmployeeAttendence(employeeAttendence);
     }

}
