package com.learner.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ismansky Maxim 19.12.2021
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path.photo}")
    private String uploadPathPhoto;

    @Value("${upload.path.books}")
    private String uploadPathBooks;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///" + uploadPathPhoto + "/");
        registry.addResourceHandler("/books/**")
                .addResourceLocations("file:///" + uploadPathBooks + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/scripts/**")
                .addResourceLocations("classpath:/static/scripts/");
    }
}
