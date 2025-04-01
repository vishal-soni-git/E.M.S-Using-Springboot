package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	// static {
    //     System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME); // Load OpenCV
    // }
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

}
