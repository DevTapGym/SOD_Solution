package web.internship.SODSolutions.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MultipartConfigLogger {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @PostConstruct
    public void init() {
        System.out.println(">>> MAX FILE SIZE CONFIG = " + maxSize);
        System.out.println(">>> MAX REQUEST SIZE CONFIG = " + maxRequestSize);
    }
}

