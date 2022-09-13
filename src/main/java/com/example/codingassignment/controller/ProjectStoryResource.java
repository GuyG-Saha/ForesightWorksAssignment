package com.example.codingassignment.controller;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.services.ProjectStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1")
public class ProjectStoryResource {
    @Autowired
    private ProjectStoryService projectStoryService;

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectStory>> getAllProjects() {
        return ResponseEntity.ok(projectStoryService.getAllProjects());
    }

    @PostMapping("/projects/new")
    public ResponseEntity<ProjectStory> addNewTaskOrSubproject(@RequestBody ProjectStory projectStory) {
        if (Objects.nonNull(projectStoryService.addNewTaskOrSubproject(projectStory)))
            return ResponseEntity.ok(projectStory);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/related/{projectStoryUid}")
    public ResponseEntity<ProjectStory> findRelated(@PathVariable String projectStoryUid) {
        if (Objects.nonNull(projectStoryService.calculateDates(projectStoryUid)))
            return ResponseEntity.ok(projectStoryService.calculateDates(projectStoryUid));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
