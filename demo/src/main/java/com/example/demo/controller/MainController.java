package com.example.demo.controller;

// import java.io.File;
import java.io.IOException;
// import java.net.http.HttpHeaders;

// import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
// import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// import com.example.demo.entity.FileEntity;
// import com.example.demo.repository.jpa.JpaFileEntityRepository;
// import com.example.demo.repository.mongo.MongoFileEntityRepository;
import com.mongodb.client.gridfs.model.GridFSFile;

// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class MainController {

    // @Autowired
    // private JpaFileEntityRepository fileRepository;

    // Max 16 MB can be stored
    // @Autowired
    // private MongoFileEntityRepository fileMongoRepository;

    // @Autowired
    // private FileUploadHelper fileUploadHelper;

    // @PostMapping("/upload-image")
    // public String uploadImage(@RequestParam("file") MultipartFile file)
    // {
    // System.out.println(file.getOriginalFilename());
    // System.out.println(file.getContentType());
    // System.out.println(file.getSize());
    // boolean val = fileUploadHelper.upload(file);
    // if(val)
    // {
    // // return "http://localhost:8080/images/"+file.getOriginalFilename();
    // return
    // ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString();
    // }else{
    // return "Uploading is unsuccessful!";
    // }
    // }

    // JPA

    // @PostMapping("/uploadMultipartFile")
    // public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile
    // file) throws IOException {
    // FileEntity fileEntity = new FileEntity();
    // fileEntity.setName(file.getOriginalFilename());
    // fileEntity.setContentType(file.getContentType());
    // fileEntity.setData(file.getBytes());

    // fileRepository.save(fileEntity);

    // return ResponseEntity.ok("File uploaded successfully! "+fileEntity.getId());
    // }

    // @GetMapping("/download/{id}")
    // public ResponseEntity<byte[]> download(@PathVariable int id) {
    // FileEntity file = fileRepository.findById(id).orElseThrow(()->new
    // RuntimeException("File not found!"));

    // return ResponseEntity
    // .ok()
    // .contentType(MediaType.parseMediaType(file.getContentType()))
    // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
    // file.getName() + "\"")
    // .body(file.getData()) ;
    // }

    // MongoDB Basics

    // @PostMapping("/uploadMongoBasic")
    // public ResponseEntity<String> uploadFileMongoBasic(@RequestParam("file")
    // MultipartFile file) throws IOException {
    // FileEntity fileEntity = new FileEntity();
    // fileEntity.setName(file.getOriginalFilename());
    // fileEntity.setContentType(file.getContentType());
    // fileEntity.setData(file.getBytes());

    // fileMongoRepository.save(fileEntity);

    // return ResponseEntity.ok("File uploaded successfully!
    // "+fileEntity.getObjectId());
    // }

    // @GetMapping("/download/mongoBasic/{id}")
    // public ResponseEntity<byte[]> downloadMongoBasic(@PathVariable ObjectId id) {
    // FileEntity file = fileMongoRepository.findById(id).orElseThrow(()->new
    // RuntimeException("File not found!"));
    // return ResponseEntity
    // .ok()
    // .contentType(MediaType.parseMediaType(file.getContentType()))
    // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
    // file.getName() + "\"")
    // .body(file.getData()) ;
    // }

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    @PostMapping("/uploadMongoDBStandard")
    public String uploadFileInMongoDB(@RequestParam("file") MultipartFile file) throws IOException {
        gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());

        return "File uploaded successfully : " + file.getOriginalFilename();
    }

    @GetMapping("/download/mongoDBStandard/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable("fileName") String fileName) throws IOException {

        // GridFSFile file = gridFsTemplate.findOne(
        // query(whereFilename().is(filename))
        // );
        Query query = new Query(GridFsCriteria.whereFilename().is(fileName));
        GridFSFile file = gridFsTemplate.findOne(query);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        GridFsResource resource = gridFsOperations.getResource(file);

        // If you make url call from browser it directly download
        // If you write 'inline' instead of 'attachment' it will display
        // NOTE
        // Remember browser will load all data in your ram and show it to you
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(resource.getInputStream()));
    }

}
