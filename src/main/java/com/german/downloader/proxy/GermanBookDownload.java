package com.german.downloader.proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GermanBookDownload {

    private final ProxyBook proxyBook = new ProxyBook();

    @GetMapping("/ger")
    void downloadGermanBook(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=\"book.zip\"");

        proxyBook.writeContent(response.getOutputStream());
        response.flushBuffer();
    }

}
