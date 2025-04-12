package com.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ems.entities.Employee;
import com.ems.service.AuthenticateService;
import com.ems.service.EmployeeService;

@Service
public class UserAuthenticate implements AuthenticateService {

    @Autowired
    EmployeeService employeeService;

    @Override
    public String authenticate(String userRole, String userEmail, String userPassword,Model model) {

        String user=userRole.trim();
        String email=userEmail.trim();
        String password=userPassword.trim();
       
        if(user.equals("admin")){
             
            if (email.equals("admin@example.com") && password.equals("password")) {

                System.out.println("Login Success as Admin........................");
                model.addAttribute("email", email);
                System.out.println(email);
                return "redirect:/admin/AdminDashboard";  // Redirect to dashboard

            } else {
                System.out.println("Login Failed as Admin........................");
                model.addAttribute("error", "Invalid Admin Email or Password");
                return "redirect:/error";  // Stay on login page with error
            }
         }

         else if(user.equals("employee"))
         {

            List<Employee> list=employeeService.getEmployeeByEmail(email);

            String emailCheck=list.get(0).getEmail();
            String passwordCheck=list.get(0).getPassword();


            if (email.equals(emailCheck) && password.equals(passwordCheck)) {

                System.out.println("Login Success as Employee........................");
                
                model.addAttribute("employee", "employee");
                model.addAttribute("email", email);
                System.out.println(email);
                return "redirect:/employee/EmployeeDashboard";  // Redirect to dashboard
            } else {

                System.out.println("Login Failed as Employee........................");
                model.addAttribute("error", "Invalid Employee Email or Password");
                return "redirect:/error";  // Stay on login page with error
            }
         }

         else{
            System.out.println("Login Failed due to userRole........................");
            model.addAttribute("error", "Invalid User");
            return "redirect:/login"; 
         }
    }

}
