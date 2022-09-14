package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;

import java.util.List;

public interface ProjectStoryService {

    public ProjectStory saveProject(ProjectStory projectStory);

    public List<ProjectStory> getAllProjects();

    public ProjectStory addNewTaskOrSubproject(ProjectStory projectStory);

    public ProjectStory calculateDates(String Uid);

    public ProjectStory findProjectByUid(String Uid);

    public boolean findProjectByParentUid(String parentUid);

    public ProjectStory applyStartDateToProject(String projectUid);

}
