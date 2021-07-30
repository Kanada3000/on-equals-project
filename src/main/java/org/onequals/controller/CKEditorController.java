package org.onequals.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.onequals.services.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
public class CKEditorController {

    private final StorageService storageService;

    public CKEditorController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/image/upload")
    public String uploadImage(@RequestPart MultipartFile upload) throws IOException {
//        storageService.uploadImage(upload);
        String sourceName = upload.getOriginalFilename();
        String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

        File destFile;
        String destFileName;

        File directory = new File("/uploads/images");
        directory.mkdirs();
        List<String> list = Arrays.asList(directory.list());
        for(String s: list){
            System.out.println(s);
        }
        do{
            destFileName = RandomStringUtils.randomAlphabetic(12).concat(".").concat(sourceExt);
            System.out.println(destFileName);
        } while (!list.contains(destFileName));
        System.out.println("Done");
        var is = upload.getInputStream();
        Files.copy(is, Paths.get("/uploads/images/" + destFileName),
                StandardCopyOption.REPLACE_EXISTING);
//        destFile = new File("/uploads/images/" + destFileName);
//        destFile.getParentFile().mkdirs();
//        upload.transferTo(destFile);
        return destFileName;
    }
}