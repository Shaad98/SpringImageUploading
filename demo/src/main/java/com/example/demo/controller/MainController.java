package com.example.demo.controller;

// import java.io.File;
import java.io.IOException;
// import java.net.http.HttpHeaders;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.FileEntity;
import com.example.demo.repository.jpa.JpaFileEntityRepository;
import com.example.demo.repository.mongo.MongoFileEntityRepository;

// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class MainController {

    @Autowired
    private JpaFileEntityRepository fileRepository;

    // Max 16 MB can be stored
    @Autowired
    private MongoFileEntityRepository fileMongoRepository;

    // @Autowired
    // private FileUploadHelper fileUploadHelper;

    // @PostMapping("/upload-image")
    // public String uploadImage(@RequestParam("file") MultipartFile file)
    // {
    //     System.out.println(file.getOriginalFilename());
    //     System.out.println(file.getContentType());
    //     System.out.println(file.getSize());
    //     boolean val = fileUploadHelper.upload(file);
    //     if(val)
    //     {
    //         // return "http://localhost:8080/images/"+file.getOriginalFilename();
    //         return ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString();
    //     }else{
    //         return "Uploading is unsuccessful!";
    //     }
    // }

    @PostMapping("/uploadMultipartFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
       FileEntity fileEntity = new FileEntity();
       fileEntity.setName(file.getOriginalFilename());
       fileEntity.setContentType(file.getContentType());
       fileEntity.setData(file.getBytes());

       fileRepository.save(fileEntity);

       return ResponseEntity.ok("File uploaded successfully! "+fileEntity.getId());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable int id) {
        FileEntity file = fileRepository.findById(id).orElseThrow(()->new RuntimeException("File not found!"));

        return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"") 
                    .body(file.getData())   ;
    }

    @PostMapping("/uploadMongoBasic")
    public ResponseEntity<String> uploadFileMongoBasic(@RequestParam("file") MultipartFile file) throws IOException {
       FileEntity fileEntity = new FileEntity();
       fileEntity.setName(file.getOriginalFilename());
       fileEntity.setContentType(file.getContentType());
       fileEntity.setData(file.getBytes());

       fileMongoRepository.save(fileEntity);

       return ResponseEntity.ok("File uploaded successfully! "+fileEntity.getObjectId());
    }
    
    @GetMapping("/download/mongoBasic/{id}")
    public ResponseEntity<byte[]> downloadMongoBasic(@PathVariable ObjectId id) {
        FileEntity file = fileMongoRepository.findById(id).orElseThrow(()->new RuntimeException("File not found!"));
        return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"") 
                    .body(file.getData())   ;
    }
    
}
