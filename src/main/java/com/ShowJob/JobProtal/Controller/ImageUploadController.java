package com.ShowJob.JobProtal.Controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageUploadController {
	
	
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
               
                String uploadDir = "src/main/resources/static/images/";
                File saveFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(saveFile);
                System.out.println("Image saved: " + saveFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/"; 
    }

	

}
