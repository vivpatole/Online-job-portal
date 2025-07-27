package com.ShowJob.JobProtal.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ShowJob.JobProtal.Entity.Application;
import com.ShowJob.JobProtal.Entity.Job;
import com.ShowJob.JobProtal.Entity.User;
import com.ShowJob.JobProtal.Repositories.ApplicationRepository;
import com.ShowJob.JobProtal.Repositories.JobRepository;
import com.ShowJob.JobProtal.Repositories.UserRepository;

@Controller
public class ApplyController {
	
	 @Autowired
	    private JobRepository jobRepository;
	 
	  @Autowired
	    private ApplicationRepository applicationRepository;
	  
	  @Autowired
	  private UserRepository userRepository;
	  public User getUserByEmail(String email) {
		    User user = userRepository.findByEmail(email);
		    if (user == null) {
		        throw new RuntimeException("User not found with email: " + email);
		    }
		    return user;
		}

	    @PostMapping("/apply/{id}")
	    public String applyForJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	        Job job = jobRepository.findById(id).orElse(null);

	        if (job != null) {   
	            System.out.println("Applied for job with ID: " + id + ", Title: " + job.getTitle());
	            redirectAttributes.addFlashAttribute("message", "You have successfully applied for " + job.getTitle());
	        } else {
	            System.out.println("Job with ID " + id + " not found.");
	            redirectAttributes.addFlashAttribute("error", "Job not found!");
	        }

	        return "redirect:/home";
	    }
	    @PostMapping("/apply")
	    public String applyForJob(@RequestParam("jobId") Long jobId,
	                              @RequestParam("resumeFile") MultipartFile resumeFile,
	                              @AuthenticationPrincipal UserDetails userDetails,
	                              RedirectAttributes redirectAttributes) {
	        if (resumeFile.isEmpty()) {
	            redirectAttributes.addFlashAttribute("error", "Please upload a resume file.");
	            return "redirect:/jobs";
	        }

	        try {
	           
	            User user = userRepository.findByEmail(userDetails.getUsername());
	            if (user == null) {
	                throw new RuntimeException("User not found");
	            }

	            
	            Job job = jobRepository.findById(jobId)
	                    .orElseThrow(() -> new RuntimeException("Job not found"));

	            String uploadDir = "uploads/resumes/";
	            Path uploadPath = Paths.get(uploadDir);
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }

	            
	            String originalFileName = Paths.get(resumeFile.getOriginalFilename()).getFileName().toString();
	            String fileName = "user_" + user.getId() + "_job_" + jobId + "_" + System.currentTimeMillis() + "_" + originalFileName;
	            Path filePath = uploadPath.resolve(fileName);

	           
	            Files.copy(resumeFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	           
	            Application application = new Application();
	            application.setUser(user);
	            application.setJob(job);
	            application.setResumePath(filePath.toString());

	            applicationRepository.save(application);

	            redirectAttributes.addFlashAttribute("message", "Applied successfully with resume!");

	        } catch (IOException e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("error", "Failed to upload resume: " + e.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("error", "Failed to apply for job: " + e.getMessage());
	        }

	        return "redirect:/jobs";
	    }

}