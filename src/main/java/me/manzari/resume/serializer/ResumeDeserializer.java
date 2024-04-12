package me.manzari.resume.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import me.manzari.resume.model.Resume;

import java.io.IOException;

public class ResumeDeserializer extends JsonDeserializer<Resume> {
    @Override
    public Resume deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        JsonNode content = node.get("content");
        Resume resume = new Resume();
        resume.setContent(content.toString());
        return resume;
    }
}