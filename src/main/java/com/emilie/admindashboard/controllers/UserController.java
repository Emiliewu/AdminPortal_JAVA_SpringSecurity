package com.emilie.admindashboard.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emilie.admindashboard.models.Role;
import com.emilie.admindashboard.models.User;
import com.emilie.admindashboard.services.RoleService;
import com.emilie.admindashboard.services.UserService;
import com.emilie.admindashboard.validators.UserValidator;

@Controller
public class UserController {
	private UserService userService;
	private RoleService roleService;
    private UserValidator userValidator;
    
    public UserController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }
    
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
    	
    	return "registrationPage.jsp";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
     
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "registrationPage.jsp";
        }
        User checkuser = userService.findByEmail(user.getEmail());
        if (checkuser != null) {
        	redirectAttributes.addFlashAttribute("error", "failed to register, please try again");
			return "redirect:/registration";
        }
        if(userService.adminexists()) {       	
        	// create a user role
        	userService.saveWithUserRole(user);
        	return "redirect:/home";
        	
        } else {        	
        	// create a admin role
        	userService.saveUserWithAdminRole(user);
        	return "redirect:/admin";
        }
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        List<User> allusers = userService.findAllUsers();
        model.addAttribute("allusers", allusers);
        return "adminPortal.jsp";
    }
    
    @RequestMapping("/login")
    public String loginpage(@Valid @ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
    	  if(error != null) {
              model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
          }
          if(logout != null) {
              model.addAttribute("logoutMessage", "Logout Successful!");
          }
    	return "loginPage.jsp";
    }
    
    @RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        String email = principal.getName();
        System.out.println(email);
        User currentUser = userService.findByEmail(email);
        model.addAttribute("currentUser", currentUser);
//        for(Role r: currentUser.getRoles()) {
//        	if(r.getName().equals("ROLE_ADMIN")) {
//        		return "redirect:/admin";
//        	}
//        }
        return "homePage.jsp";
    }
    
    // delete a user
    @RequestMapping("/admin/{userid}/delete")
    public String deleteuser(@PathVariable("userid") Long userid, Principal principal) {
    	//since only users who has admin role can access the route of /admin/**
    	// no need to double confirm if the current user is admin or not
//    	String email = principal.getName();
//    	User currentUser = userService.findByEmail(email);
    	userService.deleteuser(userid);
    	return "redirect:/admin";
    }
    // make admin
    @RequestMapping("/admin/{userid}/new")
    public String makeadmin(@PathVariable("userid") Long userid) {
    	userService.assignuseradmin(userid);
    	return "redirect:/admin";
    }
    
}
