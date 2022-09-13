package com.example.codingassignment.controller;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.services.ProjectStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ProjectStoryResource {
    @Autowired
    private ProjectStoryService projectStoryService;

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectStory>> getAllProjects() {
        return ResponseEntity.ok(projectStoryService.getAllProjects());
    }
}
