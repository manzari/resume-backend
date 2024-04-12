package me.manzari.resume.controller;


import me.manzari.resume.model.Resume;
import me.manzari.resume.repository.ResumeRepository;
import me.manzari.resume.service.Action;
import me.manzari.resume.service.ActionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class ResumeController {

    private final ResumeRepository resumeRepository;

    private final ActionService actionService;

    public ResumeController(ResumeRepository resumeRepository, ActionService actionService) {
        this.resumeRepository = resumeRepository;
        this.actionService = actionService;
    }

    @GetMapping("/resume/{id}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) {
        final Optional<Resume> resume = this.resumeRepository.findById(id);
        if (resume.isPresent()) {
            actionService.process(Action.Resume, "/resume/" + id, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.ok(resume.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/resume/{id}")
    public ResponseEntity<Resume> patchResume(@PathVariable Long id, @RequestBody Resume request) {
        final Optional<Resume> resume = this.resumeRepository.findById(id);
        if (resume.isPresent()) {
            request.setId(id);
            return ResponseEntity.ok(this.resumeRepository.save(request));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/resume")
    public ResponseEntity<Resume> postResume(@RequestBody Resume request) {
        return ResponseEntity.ok(this.resumeRepository.save(request));
    }
}

