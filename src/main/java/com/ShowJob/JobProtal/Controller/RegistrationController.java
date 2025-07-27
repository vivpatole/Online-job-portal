package com.ShowJob.JobProtal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ShowJob.JobProtal.Entity.User;
import com.ShowJob.JobProtal.Repositories.UserRepository;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @RequestMapping("/auth")
    public class AuthController {
        @GetMapping("/register")
        public String showRegisterForm(Model model) {
            return "register";
        }
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userRepository.findByUsername(user.getName()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser= userRepository.save(user);
        System.out.println("User Saved"+savedUser.getName()+",ID"+savedUser.getId());
        return "redirect:/login?success";
    }
}
