package com.german.downloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@SpringBootApplication
@RestController
public class DownloaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DownloaderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        return restTemplate;
    }

    String downloadLink = "https://doc-0s-5g-docs.googleusercontent.com/docs/securesc/ha0ro937gcuc7l7deffksulhg5h7mbp1/iasho0kaa30r6khkcvu9vpgnc8v3u0us/1503597600000/16706558120316080820/*/0B2BBJnm4YGo8TzlrZHBpbWxldVE?e=download";

    @GetMapping("/ger")
    public void downloadGermanBook(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=\"book.zip\"");

        System.out.println("successfully transfer file");

        final URL url = new URL(downloadLink);
        final InputStream istream = url.openStream();

        final byte[] buffer = new byte[1024 * 8];
        while (true) {
            final int len = istream.read(buffer);
            if (len <= 0) {
                break;
            }
            response.getOutputStream().write(buffer, 0, len);
        }
        response.flushBuffer();
    }
}
