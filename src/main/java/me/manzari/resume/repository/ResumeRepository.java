package me.manzari.resume.repository;


import me.manzari.resume.model.Resume;
import org.springframework.data.repository.CrudRepository;

public interface ResumeRepository extends CrudRepository<Resume, Long> {

}
