package me.manzari.resume.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Resume {
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    public Resume(String content) {
        this.content = content;
    }

    public Resume() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
