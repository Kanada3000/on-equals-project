package org.onequals.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@RestController
public class CKEditorController {

    @PostMapping("/image/upload")
    public String uploadImage(@RequestPart MultipartFile upload,
                              @RequestParam(name="CKEditorFuncNum", required = false) String callback,
                              HttpServletRequest request) throws IOException {
        String sourceName = upload.getOriginalFilename();
        String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();

        String destFileName;

        File directory = new File("/uploads/images");
        directory.mkdirs();
        List<String> list = Arrays.asList(directory.list());
        do {
            destFileName = RandomStringUtils.randomAlphabetic(12).concat(".").concat(sourceExt);
        } while (list.contains(destFileName));
        var is = upload.getInputStream();
        Files.copy(is, Paths.get("/uploads/images/" + destFileName),
                StandardCopyOption.REPLACE_EXISTING);
        String imgURL = request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(String.valueOf(request.getServerPort())).concat("/uploads/images/").concat(destFileName);
//        String imgURL = request.getScheme().concat("://").concat(request.getServerName()).concat("/uploads/images/").concat(destFileName);
        return "<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" +
                callback +
                ", '" +
                imgURL +
                "');</script>";
    }
}