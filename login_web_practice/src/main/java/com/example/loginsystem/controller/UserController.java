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

import com.example.loginsystem.model.User;
import com.example.loginsystem.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/")
//    public String home() {
//        return "index";
//    }
//    
//
//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginSubmit(@ModelAttribute User user, Model model) {
//        User loginUser = userService.login(user.getUsername(), user.getPassword());
//        if (loginUser != null) {
//            model.addAttribute("successMessage", "Login successful!");
//            return "login";
//        } else {
//            model.addAttribute("errorMessage", "Invalid credentials!");
//            return "login";
//        }
//    }
//
//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerSubmit(@ModelAttribute User user, Model model) {
//        userService.register(user);
//        model.addAttribute("successMessage", "Registration successful!");
//        return "register";
//    }
    @GetMapping("/")
	public String home() {
	    return "home";
	}
    
    @GetMapping("/login_normal")
    public String login_normal(Model model) {
        model.addAttribute("user", new User());
        return "login_normal";
    }
    
    @GetMapping("/login_premium")
    public String login_premium(Model model) {
        model.addAttribute("user", new User());
        return "login_premium";
    }
    
    @PostMapping("/login_normal")
    public String loginSubmit_normal(@ModelAttribute User user, Model model) {
    	User loginUser = userService.login_normal(user.getUsername(), user.getPassword());
    		if (loginUser != null) {
	          return "test_main";
	      } else {
	          model.addAttribute("errorMessage", "Invalid username/password!");
	          return "login_normal";
	      }
	 }
    
    @PostMapping("/login_premium")
    public String loginSubmit_premium(@ModelAttribute User user, Model model) {
    	User loginUser = userService.login_premium(user.getUsername(), user.getPassword());
    		if (loginUser != null) {
	          return "premium_page";
	      } else {
	          model.addAttribute("errorMessage", "Invalid username/password!");
	          return "login_premium";
	      }
	 }
    
    @GetMapping("/register_normal")
    public String register_normal(Model model) {
        model.addAttribute("user", new User());
        return "register_normal";
    }
    
    @GetMapping("/register_premium")
    public String register_premium(Model model) {
        model.addAttribute("user", new User());
        return "register_premium";
    }
    
    @PostMapping("/register_normal")
    public String registerSubmit_normal(@ModelAttribute User user, Model model) {
        userService.register_normal(user);
        model.addAttribute("successMessage", "Registration successful!");
        return "register_normal";
    }
    
    @PostMapping("/register_premium")
    public String registerSubmit_premium(@ModelAttribute User user, Model model) {
        userService.register_premium(user);
        model.addAttribute("successMessage", "Registration successful!");
        return "register_premium";
    }
    
    // @GetMapping("/logout")   vscode不需要這段，可以直接把按鈕導向之前的url，eclipse才需要
    // public String logout() {
    // 	return "login_normal";
    // }
    
    
}

