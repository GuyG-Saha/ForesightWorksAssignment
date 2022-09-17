package com.example.codingassignment;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.services.IOUtilService;
import com.example.codingassignment.services.ProjectStoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Initializer implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectStoryService projectStoryService;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<ProjectStory> allProjects = projectStoryService.getAllProjects();
        if (allProjects.size() == 0) {
            logger.info("Loading projects from json file...");
            allProjects = IOUtilService.parseAllProjectsFromFile();
            if (allProjects.size() == 0)
                throw new RuntimeException("ERROR:: File is empty");
            logger.info("Loaded " + allProjects.size() + " projects from .json file");
            allProjects.forEach(projectStoryService::saveProject);
            logger.info("Saved projects in H2 - PROJECT_STORY table");
        } else
            logger.info("Loaded " + allProjects.size() + " projects from DB");
    }
}
