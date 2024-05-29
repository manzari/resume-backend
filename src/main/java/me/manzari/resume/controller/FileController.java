package me.manzari.resume.controller;

import me.manzari.resume.service.FileSystemStorageService;
import me.manzari.resume.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class FileController {

    private final StorageService storageService;

    private final List<String> allowedFileExtensions = Arrays.asList("pdf", "jpg", "png");

    @Autowired
    public FileController(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        final Pattern pattern = Pattern.compile("([A-z0-9]{1,64})\\.([A-z0-9]{3})");
        final Matcher matcher = pattern.matcher(filename);
        if (!matcher.matches() || matcher.groupCount() != 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename not accepted");
        }
        if (!allowedFileExtensions.contains(matcher.group(2))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not accepted");
        }
        Resource file = storageService.loadAsResource(filename);

        if (file == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }
}
