package com.ems.controller;

import java.io.IOException;
import java.time.LocalDate;
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
import org.springframework.web.multipart.MultipartFile;

import com.ems.entities.Employee;
import com.ems.entities.EmployeeLeave;
import com.ems.entities.Posts;
import com.ems.service.EmployeeLeaveService;
import com.ems.service.EmployeeService;
import com.ems.service.PostsService;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    EmployeeLeaveService employeeLeaveService;

    @Autowired
    PostsService postsService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/AdminDashboard")
    public String adminDashboard() {
        return "AdminDashboard";
    }

    @GetMapping("/EmployeeManagement")
    public String getEmployeeManagement() {
        return "AdminEmployeeManagement";
    }

    @GetMapping("/employeeAdd")
    public String employeeAddPage() {

        return "EmployeeAdd";
    }

    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee employee)throws IOException {

         if (!employee.getPhotoFile().isEmpty()) {
             employee.setPhoto(employee.getPhotoFile().getBytes());
         }
         employeeService.saveEmployee(employee);

         System.out.println("Employee Save Successfully "+employee.toString());
        
        return "redirect:/admin/EmployeeManagement";
    }    


    
    @GetMapping("/leaveManagement")
    public String leaveManagement(Model model) {
        return "AdminLeaveManagement";
    }

    //creating for geting data from db to html page
    @ModelAttribute("employeeleaves")
    public List<EmployeeLeave> getEmployeeLeaves() {
        return employeeLeaveService.getAllByIdDesc();
    }

    @PostMapping("/leaveManagement/process")
    public String postMethodName(@RequestParam("action") String action, @RequestParam("requestId") String empId) {

        EmployeeLeave employeeLeave=employeeLeaveService.getEmployeeById(Integer.parseInt(empId));
       
        if(action.equals("accept"))
        {
            employeeLeave.setStatus("ACCEPT");
            System.out.println("Accept leave");
        }
        else if (action.equals("reject")) {
            employeeLeave.setStatus("REJECT");
            System.out.println("Reject Leaves");
        }
        else{
            System.out.println("some error action not found");
        }

        employeeLeaveService.save(employeeLeave);
        
        return "/AdminLeaveManagement";
    }
    

    @GetMapping("/AdminAttendence")
    public String getAdminAttendence() {
        return "AdminAttendence";
    }
    
    


    @GetMapping("/uploadUpdates")
    public String getUploadUpdate() {
        return "UploadUpdates";
    } 

     @ModelAttribute("posts")
    public List<Posts> getPosts() {

        return postsService.getAllEmployees().stream().map(post -> {
            String base64Image = Base64.getEncoder().encodeToString(post.getPost());
            post.setBase64Image(base64Image); // Store the Base64 string in the new field
            return post;
        }).collect(Collectors.toList());
       
    }

    @PostMapping("/uploadpost")
    public String postUpdates(@RequestParam("post") MultipartFile mFile, @RequestParam("title") String title) throws IOException {
        
        Posts p=new Posts();

        p.setTitle(title);
        p.setPost(mFile.getBytes());
        p.setDate(LocalDate.now());
        postsService.savePost(p);
        
        return "redirect:/admin/uploadUpdates";
    }



}
