package com.example.codingassignment.controller;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.services.ProjectStoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @PostMapping("/applyDatesToProjects/{projectStoryUid}")
    public ResponseEntity<ProjectStory> applyStartAndEndDates(@PathVariable String projectStoryUid) {
        if (!projectStoryUid.isEmpty()) {
            return ResponseEntity.ok(projectStoryService.calculateDatesForProject(projectStoryUid));
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/projects/delete/{projectStoryUid}")
    public ResponseEntity<ProjectStory> deleteTaskOrSubprojects(@PathVariable String projectStoryUid) {
        if (Objects.nonNull(projectStoryService.deleteTaskOrSubproject(projectStoryUid)))
            return ResponseEntity.ok(projectStoryService.deleteTaskOrSubproject(projectStoryUid));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/projects/getHierarchy/{Uid}")
    public String getProjectHierarchy(@PathVariable String Uid) throws JsonProcessingException {
        return projectStoryService.getJsonProjectHierarchy(Uid);
    }
}
