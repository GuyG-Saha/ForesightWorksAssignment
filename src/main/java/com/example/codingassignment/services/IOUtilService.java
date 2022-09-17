package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.datamodel.ProjectStoryStructure;
import com.example.codingassignment.datamodel.ProjectStoryWithAllDescendants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class IOUtilService {
    public final static String FILE_PATH = "C:\\Users\\ggont\\eclipse-workspace\\TraningStrings\\src\\main\\resources\\proj.json";

    public IOUtilService(ProjectStoryService projectStoryService) {

    }

    public static List<ProjectStory> parseAllProjectsFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(FILE_PATH),
                ProjectStoryStructure.class).getProjectStoryList();
    }

    public static String exportProjectHierarchy(ProjectStory theProject, List<ProjectStory> allDescendants) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ProjectStoryWithAllDescendants projectStoryWithAllDescendants = new ProjectStoryWithAllDescendants();
        projectStoryWithAllDescendants.setProjectStory(theProject);
        projectStoryWithAllDescendants.setAllDescendants(allDescendants);
        return ow.writeValueAsString(projectStoryWithAllDescendants);
    }

}
