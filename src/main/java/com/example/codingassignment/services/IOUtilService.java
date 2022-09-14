package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.datamodel.ProjectStoryStructure;
import com.example.codingassignment.datamodel.ProjectStoryWithChildren;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class IOUtilService {
    public final static String FILE_PATH = "C:\\Users\\ggont\\eclipse-workspace\\TraningStrings\\src\\main\\resources\\proj.json";
    @Autowired
    private static ProjectStoryService projectStoryService;
    private static ProjectStoryWithChildren projectStoryWithChildren;

    public IOUtilService(ProjectStoryService projectStoryService) {
        this.projectStoryService = projectStoryService;
    }

    public static List<ProjectStory> parseAllProjectsFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ProjectStory> projects = mapper.readValue(new File("C:\\Users\\ggont\\eclipse-workspace\\TraningStrings\\src\\main\\resources\\proj.json"),
                ProjectStoryStructure.class).getProjectStoryList();
        return projects;
    }

    public static String exportProjectHierarchy(String Uid) throws JsonProcessingException {
        ProjectStory theProject = projectStoryService.findProjectByUid(Uid);
        List<ProjectStory> projectChildren = projectStoryService
                .getAllProjects()
                .stream()
                .filter(c -> Objects.equals(c.getParentUid(), Uid))
                .collect(Collectors.toList());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        projectStoryWithChildren = new ProjectStoryWithChildren();
        projectStoryWithChildren.setChildren(projectChildren);
        projectStoryWithChildren.setProjectStory(theProject);
        String json = ow.writeValueAsString(projectStoryWithChildren);
        return json;
    }

}
