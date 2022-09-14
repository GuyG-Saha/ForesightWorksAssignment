package com.example.codingassignment.config;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.services.IOUtilService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class DataConfig {
    @Bean
    public List<ProjectStory> allProjects() throws IOException {
        return IOUtilService.parseAllProjectsFromFile();
    }
}
