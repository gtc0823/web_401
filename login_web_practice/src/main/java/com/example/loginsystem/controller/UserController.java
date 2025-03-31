//package com.example.loginsystem.controller;
//import com.example.loginsystem.model.User;
//import com.example.loginsystem.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/")
//    public String showHomePage() {
//        return "index";
//    }
//
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
//        User existingUser = userService.findByUsername(username);
//        if (existingUser != null && existingUser.getPassword().equals(password)) {
//            model.addAttribute("message", "Login successful");
//            return "result";
//        } else {
//            model.addAttribute("error", "Invalid username or password");
//            return "login";
//        }
//    }
//
//    @GetMapping("/register")
//    public String showRegisterPage() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
//        User newUser = new User();
//        newUser.setUsername(username);
//        newUser.setPassword(password);
//        userService.saveUser(newUser);
//        model.addAttribute("message", "User registered successfully");
//        return "result";
//    }
//}

package com.example.loginsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.loginsystem.model.NormalUser;
import com.example.loginsystem.model.PremiumUser;
import com.example.loginsystem.service.UserService;

@Controller
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        logger.info("Accessing home page");
        return "home";
    }
    
    @GetMapping("/login_normal")
    public String login_normal(Model model) {
        model.addAttribute("user", new NormalUser());
        return "login_normal";
    }
    
    @GetMapping("/login_premium")
    public String login_premium(Model model) {
        model.addAttribute("user", new PremiumUser());
        return "login_premium";
    }
    
    @PostMapping("/login_normal")
    public String loginSubmit_normal(@ModelAttribute NormalUser user, Model model) {
        NormalUser loginUser = userService.loginNormal(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            return "redirect:/document_management"; 
        } else {
            model.addAttribute("errorMessage", "Invalid username/password!");
            return "redirect:/login_normal";
        }
    }
    
    @PostMapping("/login_premium")
    public String loginSubmit_premium(@ModelAttribute PremiumUser user, Model model) {
        PremiumUser loginUser = userService.loginPremium(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            return "premium_page";
        } else {
            model.addAttribute("errorMessage", "Invalid username/password!");
            return "redirect:/login_premium";
        }
    }
    
    @GetMapping("/register_normal")
    public String register_normal(Model model) {
        model.addAttribute("user", new NormalUser());
        return "register_normal";
    }
    
    @GetMapping("/register_premium")
    public String register_premium(Model model) {
        model.addAttribute("user", new PremiumUser());
        return "register_premium";
    }
    
    @PostMapping("/register_normal")
    public String registerSubmit_normal(@ModelAttribute NormalUser user, Model model) {
        userService.registerNormal(user);
        model.addAttribute("successMessage", "Registration successful! Please login.");
        return "redirect:/login_normal";
    }
    
    @PostMapping("/register_premium")
    public String registerSubmit_premium(@ModelAttribute PremiumUser user, Model model) {
        userService.registerPremium(user);
        model.addAttribute("successMessage", "Registration successful! Please login.");
        return "redirect:/login_premium";
    }
    
    // @GetMapping("/logout")   vscode不需要這段，可以直接把按鈕導向之前的url，eclipse才需要
    // public String logout() {
    // 	return "login_normal";
    // }
    
    
}
