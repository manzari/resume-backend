package me.manzari.resume.controller;


import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.Resume;
import me.manzari.resume.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class ResumeController {

    @Autowired
    private ResumeRepository resumeRepository;

    @GetMapping("/resume/{id}")
    public ResponseEntity<Resume> getResume(@PathVariable Long id) {
        final Optional<Resume> resume = this.resumeRepository.findById(id);
        if (resume.isPresent()) {
            return ResponseEntity.ok(resume.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/resume")
    public ResponseEntity<Resume> postResume(@RequestBody Resume request) {
        return ResponseEntity.ok(this.resumeRepository.save(request));
    }
}

