package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.datamodel.ProjectStoryStructure;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileParser {
    public final static String FILE_PATH = "C:\\Users\\ggont\\eclipse-workspace\\TraningStrings\\src\\main\\resources\\proj.json";
    @Autowired
    private static ProjectStoryService projectStoryService;

    public FileParser(ProjectStoryService projectStoryService) {
        this.projectStoryService = projectStoryService;
    }

    public static List<ProjectStory> parseAllProjectsFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ProjectStory> projects = mapper.readValue(new File("C:\\Users\\ggont\\eclipse-workspace\\TraningStrings\\src\\main\\resources\\proj.json"),
                ProjectStoryStructure.class).getProjectStoryList();
        return projects;
    }

    /*public static List<ProjectStory> parseAllProjectsFromFile() throws ParseException, IOException, org.json.simple.parser.ParseException {
        List<ProjectStory> allProjects = new ArrayList<>();
        TreeNode<ProjectStory> treeNode = new TreeNode<>();
        treeNode.setAllowsChildren(true);

        Object obj = new JSONParser().parse(new FileReader(FILE_PATH));
        JSONObject jo = (JSONObject) obj;
        JSONArray items = (JSONArray) jo.get("items");
        Date sDate = null, eDate = null;

        for (int i = 0; i < items.size(); i++) {
            JSONObject jsonLineItem = (JSONObject) items.get(i);
            String uid = (String) jsonLineItem.get("uid");
            String name = (String) jsonLineItem.get("name");
            String type = (String) jsonLineItem.get("type");
            String startDate = (String) jsonLineItem.get("startDate");
            if (!Objects.equals(startDate, null))
                sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            String endDate = (String) jsonLineItem.get("endDate");
            if (!Objects.equals(endDate, null))
                eDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            String parentUid = (String) jsonLineItem.get("parentUid");

            ProjectStory p = new ProjectStory(uid, name, type, sDate, eDate, parentUid);
            treeNode.add(new TreeNode<ProjectStory>(p));
            projectStoryService.saveProject(p);
            allProjects.add(p);
        }
        System.out.println(treeNode.getChildCount());
        return allProjects;
    }*/

}
