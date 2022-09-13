package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.repository.ProjectStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectStoryServiceImpl implements ProjectStoryService {
    @Autowired
    public ProjectStoryRepository projectStoryRepository;

    public ProjectStoryServiceImpl(ProjectStoryRepository projectStoryRepository) {
        this.projectStoryRepository = projectStoryRepository;
    }

    @Override
    public ProjectStory saveProject(ProjectStory projectStory) {
        return projectStoryRepository.save(projectStory);
    }

    public List<ProjectStory> getAllProjects() {
        List<ProjectStory> allProjects = new ArrayList<>();
        projectStoryRepository
                .findAll()
                .iterator()
                .forEachRemaining(allProjects::add);
        return allProjects;
    }

    @Override
    public ProjectStory addNewTaskOrSubproject(ProjectStory projectStory) {
        if (!Objects.equals( projectStory.getType(), "TYPE")) {
            return null;
        } else {
            return projectStoryRepository.save(projectStory);
        }
    }
}
