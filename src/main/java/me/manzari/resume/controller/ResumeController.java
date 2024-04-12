package me.manzari.resume.controller;


import me.manzari.resume.model.Resume;
import me.manzari.resume.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ResumeController {

    @Autowired
    private ResumeRepository resumeRepository;

    @GetMapping("/resume")
    public Resume resume() {
        final Long id = 1L;
        final Optional<Resume> resume = this.resumeRepository.findById(id);

        return resume.orElse(null);
    }
}

