package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.entities.Employee;
import com.ems.service.AuthenticateService;
import com.ems.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EMSController {

    @Autowired
    EmployeeService es;

    @Autowired
    AuthenticateService userAuthenticate;

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session on logout
        return "index";
    }

    @GetMapping("/login")
    public String HomePage() {
        return "index";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam("userRole") String user, @ModelAttribute("employee") Employee employee,Model model,HttpSession session) {    
       
        session.setAttribute("employeeEmail", employee.getEmail());
        
        System.out.println("Setting Session "+session.getAttribute("employeeEmail"));
      
        System.out.println("Login proceed.............");
        return userAuthenticate.authenticate(user, employee.getEmail(), employee.getPassword(), model);
        
    }


}
