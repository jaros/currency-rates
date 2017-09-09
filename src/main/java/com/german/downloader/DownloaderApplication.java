package com.german.downloader;

import com.german.downloader.proxy.ProxyBook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@RestController
public class DownloaderApplication {


    private final ProxyBook proxyBook = new ProxyBook();

    public static void main(String[] args) {
        SpringApplication.run(DownloaderApplication.class, args);
    }

    @GetMapping("/welcome")
    String welcome() {
        return "Welcome to the ultimate downloader";
    }

    @GetMapping("/ger")
    void downloadGermanBook(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=\"book.zip\"");

        proxyBook.writeContent(response.getOutputStream());
        response.flushBuffer();
    }

}
