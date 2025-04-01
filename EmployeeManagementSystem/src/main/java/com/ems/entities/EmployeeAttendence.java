package com.ems.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity

public class EmployeeAttendence {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

  
    private int empId;

    @Column
    private LocalDate date;

    @Column
    private String status;

    @Column
    private LocalTime time;

}
