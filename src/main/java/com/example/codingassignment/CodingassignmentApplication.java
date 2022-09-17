package com.example.codingassignment;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.services.IOUtilService;
import com.example.codingassignment.services.ProjectStoryService;
import com.example.codingassignment.services.ProjectStoryServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class CodingassignmentApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(CodingassignmentApplication.class, args);
		ProjectStoryService projectStoryService = configurableApplicationContext.getBean(ProjectStoryServiceImpl.class);
		// Load from DB
		List<ProjectStory> allProjects = projectStoryService.getAllProjects();
		if (allProjects.size() == 0) {
			System.out.println("INFO:: Loading projects from file...");
			allProjects = IOUtilService.parseAllProjectsFromFile();
			if (allProjects.size() == 0)
				throw new RuntimeException("ERROR:: File is empty");
			System.out.println("INFO:: Loaded " + allProjects.size() + " projects from .json file");
			allProjects.forEach(projectStoryService::saveProject);

			System.out.println("INFO:: Saved projects in H2 DB");
		} else
			System.out.println("INFO:: Loaded " + allProjects.size() + " projects from DB");

	}

}
