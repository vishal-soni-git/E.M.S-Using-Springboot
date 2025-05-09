package com.ems.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ems.entities.Employee;
import com.ems.entities.EmployeeLeave;
import com.ems.entities.Posts;
import com.ems.service.EmployeeAttendenceService;
import com.ems.service.EmployeeLeaveService;
import com.ems.service.EmployeeService;
import com.ems.service.PostsService;

import org.springframework.web.bind.annotation.PostMapping;

import com.ems.entities.EmployeeAttendence;


@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    EmployeeLeaveService employeeLeaveService;

    @Autowired
    PostsService postsService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeAttendenceService employeeAttendenceService;

    @GetMapping("/AdminDashboard")
    public String adminDashboard() {

        System.out.println("Admin page Open ++++++++++++++++");
        return "AdminDashboard";
    }

    @GetMapping("/adminProfile")
    public String getMethodName() {
        return "AdminProfile";
    }
    
    @GetMapping("/showMessage")
    public String showMessagePage() {
        return "Message"; // This will render Message.html
    }

    @GetMapping("/EmployeeManagement")
    public String getEmployeeManagement() {
        return "AdminEmployeeManagement";
    }

    @GetMapping("/employeeAdd")
    public String employeeAddPage() {
        System.out.println("Employee Add page open ++++++++++++");
        return "EmployeeAdd";
    }

    @GetMapping("/employeeRemove")
    public String getEmployeeRemove() {
        System.out.println("Inside EmployeeRemove Page");
        return "EmployeeRemove";
    }

    @GetMapping("/employeeShow")
    public String getEmployeeShow() {
        return "EmployeeShow";
    }
    
    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee employee,RedirectAttributes redirectAttributes)throws IOException {

         if (!employee.getPhotoFile().isEmpty()) {
             employee.setPhoto(employee.getPhotoFile().getBytes());
         }
         employeeService.saveEmployee(employee);

         redirectAttributes.addFlashAttribute("message", "Employee ID: "+employee.getId()+" Save Successfully!");

         System.out.println("Employee Save Successfully ");

      return "redirect:/admin/showMessage";
    }    

    @PostMapping("/find")
    public String findEmployee(@RequestParam("empId") Integer id,@RequestParam("pageName") String pageName,
     Model model) {

        System.out.println("Inside find method");
        // You can use ID or name depending on your requirement
         Optional<Employee> employee = employeeService.findById(id);


        if (employee != null) {
            if (employeeService.isEmployeeExist(id)) {

                model.addAttribute("employee", employee.get());

                String base64Photo = employeeService.convertIntoBase64Photo(employee.get().getPhoto());
               
                 model.addAttribute("image", base64Photo); 
            }
            else
            {
                model.addAttribute("error", "Employee not found.");
            }
          
        } else {
            model.addAttribute("error", "Employee not found.");
        }
        return "/"+pageName; // same page name as the form
    }

    @PostMapping("/delete")
    public String postMethodName(@RequestParam("empId") Integer id,RedirectAttributes redirectAttributes) {
       System.out.println("Inside Delete Method");


       boolean b=employeeService.deleteEmployee(id);
        
       if (b) {
        System.out.println("Employee Delete Successfully ");

        redirectAttributes.addFlashAttribute("message", "Employee ID: "+id+" Delete Successfully!");
       }
       else
       {
        redirectAttributes.addFlashAttribute("error", "Employee Not Delete Or Not Exist");
       }
        
     return "redirect:/admin/showMessage";
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
    public String leaveManagementProcess(@RequestParam("action") String action, @RequestParam("requestId") String empId) {

       employeeLeaveService.leaveProcess(action, empId);
        
        return "/AdminLeaveManagement";
    }
    

      //creating for geting data from db to html page
      @ModelAttribute("employeeAttendences")
      public List<EmployeeAttendence> getEmployeeAttendence(Model model) {
        List<EmployeeAttendence> list= employeeAttendenceService.getAttendaceByDate(LocalDate.now());
        return list;
      }

    @GetMapping("/AdminAttendence")
    public String getAdminAttendence() {
        return "AdminAttendence";
    }
    

    @PostMapping("/employeeAttendences/process")
    public String employeeAttendanceProcess(@RequestParam("action") String action, @RequestParam("requestId") String id) {
        
      employeeAttendenceService.markOrUnmarkAttendence(action, id);
        
        return "/AdminAttendence";
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
