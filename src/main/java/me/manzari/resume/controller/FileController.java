package me.manzari.resume.controller;

import me.manzari.resume.exceptions.StorageFileNotFoundException;
import me.manzari.resume.model.FilesResponse;
import me.manzari.resume.service.Action;
import me.manzari.resume.service.ActionService;
import me.manzari.resume.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class FileController {

    private final StorageService storageService;

    private final ActionService actionService;

    private final List<String> allowedFileExtensions = Arrays.asList("pdf", "jpg", "png");

    public FileController(StorageService storageService, ActionService actionService) {
        this.storageService = storageService;
        this.actionService = actionService;
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
        actionService.process(Action.File, "/file/" + filename, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            Resource file = storageService.loadAsResource(filename);
            if (file == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (StorageFileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
    }

    @GetMapping("/files")
    public ResponseEntity<FilesResponse> getFiles() {
        return ResponseEntity.ok(new FilesResponse(storageService.listAll()));
    }

    @DeleteMapping("/file/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        storageService.delete(filename);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/file/{filename}")
    public ResponseEntity<?> handleFileUpload(@PathVariable String filename, @RequestPart("file") MultipartFile file) {
        if (file.getOriginalFilename() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        storageService.store(file, filename);
        return ResponseEntity.ok(null);
    }
}
