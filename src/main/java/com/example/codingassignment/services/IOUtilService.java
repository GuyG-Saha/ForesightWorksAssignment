package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.datamodel.ProjectStoryStructure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class IOUtilService {
    public final static String FILE_PATH = "C:\\Users\\ggont\\eclipse-workspace\\TraningStrings\\src\\main\\resources\\proj.json";
    @Autowired
    private static ProjectStoryService projectStoryService;

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
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(theProject);
        return json;
    }

}
