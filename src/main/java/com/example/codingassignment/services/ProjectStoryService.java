package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.List;

public interface ProjectStoryService {

    public ProjectStory saveProject(ProjectStory projectStory);

    public List<ProjectStory> getAllProjects();

    public ProjectStory addNewTaskOrSubproject(ProjectStory projectStory);

    public ProjectStory findProjectByUid(String Uid);

    public boolean findProjectByParentUid(String parentUid);

    public ProjectStory calculateDatesForProject(String projectUid);

    public ProjectStory calculateCompletionStatus(String projectUid);

    public ProjectStory calculateCompletionStatusByProvidedDate(String projectUid, Date date);

    public ProjectStory deleteTaskOrSubproject(String Uid);

    public String getJsonProjectHierarchy(String Uid) throws JsonProcessingException;

}
