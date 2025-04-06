package com.explorateurmboa.article_management.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static Logger logger = LoggerFactory.getLogger(WebConfig.class);
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploads = Paths.get("article-management/src/main/resources/static/uploads");
        String uploadDir = uploads.toAbsolutePath().toUri().toString();
        logger.info("Upload directory: " + uploadDir);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir);
    }
} 