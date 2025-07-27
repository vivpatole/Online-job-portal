package com.ShowJob.JobProtal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ShowJob.JobProtal.Entity.Job;
import com.ShowJob.JobProtal.Entity.SaveJob;
import com.ShowJob.JobProtal.Entity.User;
import com.ShowJob.JobProtal.Repositories.ApplicationRepository;
import com.ShowJob.JobProtal.Repositories.JobRepository;
import com.ShowJob.JobProtal.Repositories.SavedJobRepository;
import com.ShowJob.JobProtal.Repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedJobRepository savedJobRepository;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/jobs";
    }

    @GetMapping("/jobs")
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "jobs";
    }

    @GetMapping("/add")
    public String showJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job_form";
    }

    @PostMapping("/add")
    public String addJob(@ModelAttribute Job job,
                         @AuthenticationPrincipal UserDetails userDetails,
                         RedirectAttributes redirectAttributes) {

        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("error", "User not authenticated.");
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Authenticated user not found in database.");
            return "redirect:/login";
        }

        job.setPostedBy(user);
        job.setPostedDate(new Date());
        jobRepository.save(job);

        redirectAttributes.addFlashAttribute("success", "Job posted successfully!");
        return "redirect:/jobs";
    }

    @GetMapping("/search-job")
    public String searchJobs(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Job> jobs = (keyword != null && !keyword.isEmpty())
                ? jobRepository.findByTitleContainingIgnoreCase(keyword)
                : jobRepository.findAll();

        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        return "search-job";
    }

    @GetMapping("/jobs/search")
    public String searchJobsAdvanced(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Job> jobs = (keyword != null && !keyword.isEmpty())
                ? jobRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                : jobRepository.findAll();

        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        return "search-jobs";
    }

    @PostMapping("/save-job/{id}")
    public String saveJob(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login";

        User user = userRepository.findByUsername(userDetails.getUsername());
        Optional<Job> jobOpt = jobRepository.findById(id);

        if (user != null && jobOpt.isPresent()) {
            SaveJob saveJob = new SaveJob();
            saveJob.setUser(user);
            saveJob.setJob(jobOpt.get());
            savedJobRepository.save(saveJob);
        }

        return "redirect:/search-job";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login";

        User user = userRepository.findByUsername(userDetails.getUsername());
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "profile";
    }
    @GetMapping("/post-job")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());  
        return "post-job";  
    }
    @PostMapping("/post-job")
    public String submitJob(@Validated @ModelAttribute("job") Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "post-job";  
        }
        jobRepository.save(job);
        return "redirect:/jobs";
    }
}
