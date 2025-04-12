package com.ems.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ems.entities.Employee;
import com.ems.entities.EmployeeAttendence;
import com.ems.entities.EmployeeLeave;
import com.ems.entities.Posts;
import com.ems.service.EmployeeAttendenceService;
import com.ems.service.EmployeeLeaveService;
import com.ems.service.EmployeeService;
import com.ems.service.PostsService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/employee")
public class EmployeePageController {

    @Autowired
    EmployeeService es;
    
    @Autowired
    EmployeeLeaveService employeeLeaveService;

    @Autowired
    PostsService postsService;

    @Autowired
    EmployeeAttendenceService employeeAttendenceService;


    @GetMapping("/EmployeeDashboard")
    public String employeeDashboard() {
        System.out.println("+++++++ Employee Page Open ++++++");
        return "EmployeeDashboard"; // dashboard.html in templates folder
    }

    @GetMapping("/EmployeeProfile")
    public String employeeProfile(HttpSession session, Model model) {

        String employeeEmail=(String) session.getAttribute("employeeEmail");

        System.out.println("Comes in profile page");

        System.out.println("There are session in profile page:-  "+employeeEmail);

        if (employeeEmail != null) {
            List<Employee> l=es.getEmployeeByEmail(employeeEmail);
            System.out.println("Fetched data for profile page "+l);

            if (!l.isEmpty()) {
                Employee emp = l.get(0); // Assuming the employee is unique by email
                // Convert the photo to base64
                String base64Photo = emp.getPhoto() != null ? Base64.getEncoder().encodeToString(emp.getPhoto()) : null;
               // emp.setPhoto(base64Photo);  // Set the base64 encoded photo
                model.addAttribute("employee", emp);  // Add the single employee object (not list) to model
                model.addAttribute("image", base64Photo);
            }
          
          //  model.addAttribute("employee", l);
            return "EmployeeProfile"; // Return profile view
        }
        return "redirect:/login";
    }
    
    @ModelAttribute("EmployeeLeaves")
    public List<EmployeeLeave> getEmployeeLeaves(HttpSession session) {

        List<Employee> emp= es.getEmployeeByEmail((String)session.getAttribute("employeeEmail"));

        System.out.println("  id emp in leave page "+emp.get(0).getId());

        List<EmployeeLeave> list=employeeLeaveService.getByEmployeeIdSortedDesc(emp.get(0).getId());

        return list;
    }

     @GetMapping("/EmployeeLeave")
    public String employeeLeave(Model model) {
      //  model.addAttribute("employeeLeaves", employeeLeaveService.getAllByIdDesc());
        return "EmployeeLeave";  // Refers to EmployeeLeave.html in templates
    }

    @PostMapping("/EmployeeLeave/process")
    public String postMethodName(@ModelAttribute EmployeeLeave employeeLeave, Model model) {
        System.out.println("Employee Leave Data ...  " + employeeLeave);

        if (employeeLeave.getStatus() == null) {
            employeeLeave.setStatus("PENDING");
        }

        employeeLeaveService.save(employeeLeave);
        System.out.println("......................Employee Leave Data saved....................");

        return "redirect:/employee/EmployeeLeave"; // Redirect to GET endpoint
    }
    

    // Employee Attendence Code Working
    @GetMapping("/EmployeeAttendence")
    public String employeeAttendence() {
        return "EmployeeAttendence";
    }

    @PostMapping("/mark")
    public String markAttendance(@RequestParam("employeeId") String employeeId, RedirectAttributes redirectAttributes) {
        Employee employee = es.findById(Integer.parseInt(employeeId))
                .orElseThrow(() -> new RuntimeException("Employee not found"));

                if(Integer.parseInt(employeeId)==employee.getId()){

                    if(employeeAttendenceService.hasMarkedAttendanceToday(employee.getId(), LocalDate.now())){
 
                     //   model.addAttribute("message", "Attendance Already Marked!");
                        redirectAttributes.addFlashAttribute("message", "Attendance Already Marked!");
                        return "redirect:/employee/EmployeeAttendence";
                        
                    }
                   else{
                    EmployeeAttendence ea=new EmployeeAttendence();
                    ea.setEmpId(employee.getId());
                    ea.setDate(LocalDate.now());
                    ea.setTime(LocalTime.now().withNano(0));
                    ea.setStatus("PRESENT");
           
                    employeeAttendenceService.saveEmployeeAttendence(ea);
           
                //    model.addAttribute("employeeDetails", employee);
                //    model.addAttribute("attendance", ea);
                //    model.addAttribute("message", "Attendance Marked Successfully!");

                   redirectAttributes.addFlashAttribute("employeeDetails", employee);
                   redirectAttributes.addFlashAttribute("attendance", ea);
                   redirectAttributes.addFlashAttribute("message", "Attendance Marked Successfully!");

                   return "redirect:/employee/EmployeeAttendence"; // Refers to a Thymeleaf page for confirmation
                   }
                }
                else
                return "redirect:/error";
    }

//Employee Updates Page Working Code
    @GetMapping("/EmployeeUpdates")
    public String employeeUpdates(Model model) {


         List<Posts> lp= postsService.getAllEmployees().stream().map(post -> {
            String base64Image = Base64.getEncoder().encodeToString(post.getPost());
            post.setBase64Image(base64Image); // Store the Base64 string in the new field
            return post;
        }).collect(Collectors.toList());

        model.addAttribute("posts", lp);

        return "EmployeeUpdates";
    }

    @GetMapping("/resetPassword")
    public String getMethodName() {
        return "ResetPassword";
    }

    @PostMapping("/resetpasswordProcess")
    public String postMethodName(@RequestParam("oldPassword") String oldPassword,@RequestParam("confirmPassword") String newPassword,
    HttpSession session) {
        List<Employee> employee= es.getEmployeeByEmail((String)session.getAttribute("employeeEmail"));

        Employee emp=employee.get(0);

        if(emp.getPassword().equals(oldPassword)){

            emp.setPassword(newPassword);
            es.saveEmployee(emp);
            return "redirect:/employee/resetPassword";
        }
        else
        return "redirect:/error";
    }
    

}
