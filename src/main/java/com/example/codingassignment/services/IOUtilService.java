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
import java.util.*;
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
        List<ProjectStory> projectChildren = getChildren(theProject);
        List<ProjectStory> result = new ArrayList<>();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        processRecursionForHierarchy(theProject, result);
        projectStoryWithChildren = new ProjectStoryWithChildren();
        projectStoryWithChildren.setProjectStory(theProject);
        projectStoryWithChildren.setAllDescendants(result);
        System.out.println("INFO:: num of descendants by recursion: " + result.size());
        String json = ow.writeValueAsString(projectStoryWithChildren);
        return json;
    }

    private static void processRecursionForHierarchy(ProjectStory parent, List<ProjectStory> result) {
        for (ProjectStory child : getChildren(parent)) {
            result.add(child);
            processRecursionForHierarchy(child, result);
        }
    }

    private static List<ProjectStory> getChildren(ProjectStory parent) {
        return projectStoryService
                .getAllProjects()
                .stream()
                .filter(c -> Objects.equals(c.getParentUid(), parent.getUid()))
                .collect(Collectors.toList());
    }

    private static List<ProjectStory> findAllDescendants(ProjectStory parent, List<ProjectStory> children) {
        Set<ProjectStory> knownParents = new HashSet<>();
        List<ProjectStory> result = new ArrayList<>();
        for (ProjectStory child : children) {
            if (Objects.equals(child.getParentUid(), parent.getUid())) {
                result.add(child);
                if(!knownParents.contains(child)){
                    knownParents.add(child);
                    result.addAll(findAllDescendants(child, children));
                }
            }
        }
        return result;
    }

}
