package com.example.findjob.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.findjob.entity.User;
import com.example.findjob.service.LoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/findjob")
public class RegistrationController {
    

    private LoginService loginService;

    public RegistrationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping()
    public String getFindjob(){
        return "index";
    }

    @GetMapping("/signup")
    public String getSignup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@RequestParam String username, @RequestParam String password, 
    @RequestParam String confirmPassword, HttpSession session, Model model){

        if (!loginService.usernameExists(username)){
            if (username.length() > 5 && password.equals(confirmPassword)){
                User user = new User(username, password);
                loginService.saveUser(user);
                session.setAttribute("user", user);

                return "redirect:/findjob/welcome";
            }  
        }
        model.addAttribute("error", "Username taken or issue with password. Please try again.");
        return "signup";

    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String username, @RequestParam String password, HttpSession session,HttpServletResponse response,Model model){
        if (loginService.usernameExists(username)){
            User copyUserToCheck = loginService.getUserByUsername(username);
            String copyPasswordToCheck = copyUserToCheck.getPassword();
            if (copyPasswordToCheck.equals(password)){
                session.setAttribute("user", copyUserToCheck);

                // Create and add cookie
                try {
                    String encodedUsername = URLEncoder.encode(username, "UTF-8");
                    Cookie cookie = new Cookie("user", encodedUsername);
                    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    cookie.setHttpOnly(true);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                } catch (UnsupportedEncodingException e) {
                    model.addAttribute("error", "Error encoding username for cookie.");
                    return "login";
                }


                return "redirect:/findjob/welcome";
            }
        }

        model.addAttribute("error", "Invalid username or password. Please try again.");
        return "login";
    }

}
