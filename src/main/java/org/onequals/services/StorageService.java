package org.onequals.services;

import org.onequals.domain.City;
import org.onequals.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StorageService {

    public void uploadFile(MultipartFile file, String username) {

        int number;

        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }


        try {
            var is = file.getInputStream();

            File directory = new File("/uploads/resumes/" + username);
            boolean bool = directory.mkdirs();
            if (bool || directory.listFiles().length == 0) {
                number = 0;
            } else {
                List<String> list = Arrays.asList(directory.list());
                number = list.stream().mapToInt(v -> Integer.parseInt(v.substring(v.lastIndexOf("_") + 1, v.length() - 4))).max().getAsInt();
            }

            Files.copy(is, Paths.get("/uploads/resumes/" + username + "/cv_" + (number + 1) + ".pdf"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

            var msg = String.format("Failed to store file %f", file.getName());

            throw new StorageException(msg, e);
        }
    }

    public void uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        try {
            var is = file.getInputStream();

            File directory = new File("/uploads/images");
            boolean bool = directory.mkdirs();

            Files.copy(is, Paths.get("/uploads/images/" + file.getName()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

            var msg = String.format("Failed to store file %f", file.getName());

            throw new StorageException(msg, e);
        }
    }

    @Transactional
    public List<Object> findFiles(User user, Boolean approved) {
        List<Object> list = new ArrayList<Object>();

        File directory = new File("/uploads/resumes/" + user.getUsername());
        if (directory.listFiles() != null)
            for (File f : directory.listFiles()) {
                if (!approved && !f.getName().startsWith("approved")) {
                    HashMap<Object, Object> files = new HashMap<Object, Object>();
                    double val = f.length() / 1024 * 100;
                    val = Math.round(val);
                    val = val / 100;
                    files.put("size", val);
                    files.put("path", f.getPath());
                    files.put("name", f.getName());
                    list.add(files);
                }

                if (approved && f.getName().startsWith("approved")) {
                    HashMap<Object, Object> files = new HashMap<Object, Object>();
                    double val = f.length() / 1024 * 100;
                    val = Math.round(val);
                    val = val / 100;
                    files.put("size", val);
                    files.put("path", f.getPath());
                    files.put("name", f.getName());
                    list.add(files);
                }
            }
        return list;
    }

    @Transactional
    public void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    @Transactional
    public void renameFile(String path, Boolean approve) {
        File source = new File(path);
        path = path.replace(source.getName(), "");
        if (approve && !source.getName().startsWith("approved")) {
            if (source.renameTo(new File(path + "approved_" + source.getName()))) {
            }
        }

        if (!approve && source.getName().startsWith("approved")) {
            String name = source.getName();
            source.renameTo(new File(path + name.substring(9)));
        }
    }

    @Transactional
    public List<String> getAllPaths(Boolean approved) {
        File source = new File("/uploads/resumes");
        List<String> list = new ArrayList<>();
        if (source.listFiles() != null) {
            for (File f : source.listFiles()) {
                if (f.listFiles() != null) {
                    for (File g : f.listFiles()) {
                        if (approved && g.getName().startsWith("approved"))
                            list.add(g.getPath());
                        if (!approved && !g.getName().startsWith("approved"))
                            list.add(g.getPath());
                    }
                }
            }
            return list.stream().map(v -> v.substring(17)).collect(Collectors.toList());
        } else return null;
    }

    public int countFiles() {
        File source = new File("/uploads/resumes");
        int count = 0;
        if (source.listFiles() != null) {
            for (File f : source.listFiles()) {
                if (f.listFiles() != null)
                    count = count + (int)Arrays.stream(f.listFiles()).filter(v -> !v.getName().contains("approved")).count();
            }
        }
        return count;
    }

    @Transactional
    public void removeFile(String path){
        File source = new File(path);
        source.delete();
    }

    public String randomName() {
        int len = 40;
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    @Transactional
    public Page<String> findPaginated(Pageable pageable, List<String> pageList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<String> list;

        if (pageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pageList.size());
            list = pageList.subList(startItem, toIndex);
        }
        return new PageImpl<String>(list, PageRequest.of(currentPage, pageSize), pageList.size());
    }
}