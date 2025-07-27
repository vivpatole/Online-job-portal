package com.ShowJob.JobProtal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ShowJob.JobProtal.Entity.Job;
import com.ShowJob.JobProtal.Repositories.JobRepository;


@Controller
public class HomeController {
	
	@Autowired
    private JobRepository jobRepository;
	
	@GetMapping("/home")
    public String homePage(Model model) {
        
        List<Job> jobs = jobRepository.findAll();
        model.addAttribute("jobs", jobs);
        return "home"; 
    }

}
