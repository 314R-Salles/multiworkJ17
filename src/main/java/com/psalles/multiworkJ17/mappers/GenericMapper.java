package com.psalles.multiworkJ17.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Component
@Slf4j
public class GenericMapper {

    private final ObjectMapper mapper;

    public GenericMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <R> R parseObjectFromJson(String json, Class<R> responseClass) {
        try {
            if (!json.equals(EMPTY)) {
                return mapper.readValue(json, responseClass);
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("Parsing error", e);
            throw new RuntimeException("Parsing error");
        }
    }

    public String formatRequestToJson(Object request) {
        try {
            return mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
