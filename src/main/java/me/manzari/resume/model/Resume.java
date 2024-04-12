package me.manzari.resume.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import me.manzari.resume.serializer.ResumeDeserializer;
import me.manzari.resume.serializer.ResumeSerializer;

@JsonSerialize(using = ResumeSerializer.class)
@JsonDeserialize(using = ResumeDeserializer.class)
@Entity
public class Resume {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 12800)
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
