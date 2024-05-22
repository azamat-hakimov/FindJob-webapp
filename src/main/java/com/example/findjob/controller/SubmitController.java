package com.example.findjob.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.findjob.entity.Employee;
import com.example.findjob.entity.User;
import com.example.findjob.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/findjob")
public class SubmitController {

    private List<String> employees = new ArrayList<>();

    private EmployeeService employeeService;

    public SubmitController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/welcome")
    public String getWelcome(HttpSession session){
        User user = (User)session.getAttribute("user");
        long ownerId = user.getId();
        Employee employee = employeeService.displayEmployee(ownerId);
        if (employeeService.checkUserSubmittedBefore(ownerId)){
            session.setAttribute("employee", employee);
            return "redirect:/findjob/my-profile";
        }
        return "welcome";
    }

    @GetMapping("/my-profile")
    public String getMyProfile(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/findjob/welcome";
        }
        model.addAttribute("employee", employee);
        return "my-profile";
    }

    @GetMapping("/submit")
    public String getSubmit(){
        return "submit";
    }

    @PostMapping("/submit")
    public String postSubmit(@RequestParam String occupation, @RequestParam String message, HttpSession session){

        String ready_occupation = occupation.toLowerCase();
        User user = (User)session.getAttribute("user");
        if (user == null){
            return "redirect:/findjob/login";
        }
        long ownerId = user.getId();
        if (!employeeService.checkUserSubmittedBefore(ownerId)){
            employeeService.setEmployee(new Employee(ownerId,ready_occupation,message));
            return "redirect:/findjob/my-profile";
        }
        return "submit";
    }

    @GetMapping("/search")
    public String getSearch(){
        return "search";
    }

    @PostMapping("/search")
    public String postSearch(@RequestParam String search_occupation, Model model){
        String readySearch_occuaption = search_occupation.toLowerCase();
        List<Employee> employee = employeeService.getEmployeeByOccupations(readySearch_occuaption);

        employees.clear();

        for (Employee e: employee){
            employees.add("Occupation: " + e.getOccupation() 
            + "<br>About employee and contact details: " + e.getMsg());
        
        }

        model.addAttribute("employees", employees);

        return "redirect:/findjob/results";
    }

    @GetMapping("/results")
    public String getSearchResults(Model model){
        model.addAttribute("employees",employees);
        return "results";
    }
}
