package com.example.demo.helper;

import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.OutputStream;
// import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
    private String UPLOAD_DIR = "D:\\FINAL_YEAR\\DEVELOPMENT\\BOOT\\ImageUploading\\SpringImageUploading\\demo\\src\\main\\resources\\static\\images";

    public boolean upload(MultipartFile multipartFile)
    {
        boolean val = false;
        try {
            // InputStream is = multipartFile.getInputStream();
            // byte[] data = new byte[is.available()];
            // is.read(data);
        
            // FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename());
            // fos.write(data);

            // fos.flush();
            // fos.close();
            // is.close();
        
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
            val = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return val;
    }
}
