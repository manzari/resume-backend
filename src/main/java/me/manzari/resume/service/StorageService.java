package me.manzari.resume.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    void store(MultipartFile file, String filename);

    Path load(String filename);

    Resource loadAsResource(String filename);

    String[] listAll();

    void delete(String filename);

}