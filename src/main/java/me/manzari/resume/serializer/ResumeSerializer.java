package me.manzari.resume.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import me.manzari.resume.model.Resume;

import java.io.IOException;
import java.util.Map;

public class ResumeSerializer extends StdSerializer<Resume> {

    public ResumeSerializer() {
        this(null);
    }

    public ResumeSerializer(Class<Resume> t) {
        super(t);
    }

    @Override
    public void serialize(
            Resume resume, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> map = mapper.readValue(resume.getContent(), Map.class);

        jgen.writeStartObject();
        jgen.writeNumberField("id", resume.getId());
        jgen.writeObjectField("content", map);
        jgen.writeEndObject();
    }
}