package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.helper.FileUploadHelper;

@RestController
public class MainController {

    
    @Autowired
    private FileUploadHelper fileUploadHelper;

    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("file") MultipartFile file)
    {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        System.out.println(file.getSize());
        boolean val = fileUploadHelper.upload(file);
        if(val)
        {
            return "http://localhost:8080/images/"+file.getOriginalFilename();
        }else{
            return "Uploading is unsuccessful!";
        }
    }
}
