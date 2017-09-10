package com.german.downloader.rates;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class RemoteJson {

    private static final Logger logger = LoggerFactory.getLogger(RemoteJson.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static <T> Optional<T> fetch(String url, Class<T> returnedType) {
        try {
            return Optional.of(objectMapper.readValue(new URL(url), returnedType));
        } catch (IOException e) {
            logger.error("cannot get exchange rate", e);
            return Optional.empty();
        }
    }

}
