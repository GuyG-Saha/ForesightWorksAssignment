package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.repository.ProjectStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectStoryServiceImpl implements ProjectStoryService {
    @Autowired
    public ProjectStoryRepository projectStoryRepository;
    @Autowired
    private List<ProjectStory> allProjects;

    public ProjectStoryServiceImpl(ProjectStoryRepository projectStoryRepository, List<ProjectStory> allProjects) {
        this.projectStoryRepository = projectStoryRepository;
        this.allProjects = allProjects;
    }

    @Override
    public ProjectStory saveProject(ProjectStory projectStory) {
        return projectStoryRepository.save(projectStory);
    }

    public void setAllProjects(List<ProjectStory> allProjects) {
        this.allProjects = allProjects;
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
        // Check if parentUid is null
        if (!projectStory.getParentUid().isEmpty()) {
            // Task or Subproject
            if (projectStory.getType().equalsIgnoreCase("TASK")) {
                // Task
                return projectStoryRepository.save(projectStory);
            } else if (projectStory.getType().equalsIgnoreCase("PROJECT")) {
                // Subproject - check if there is a related TASK
                if (findProjectByParentUid(projectStory.getUid()))
                    return projectStoryRepository.save(projectStory);
                return null;
            } else
                return null;
        }
            System.out.println("ERROR:: parentUid is empty or null");
            return null;
        }

        public ProjectStory calculateDates(String projectStoryId) {
        ProjectStory p = allProjects
                .stream()
                .filter(project -> Objects.equals(project.getUid(), projectStoryId))
                .collect(Collectors.toList()).get(0);
            if (findProjectByParentUid(projectStoryId)) {
                List<ProjectStory> related = allProjects.stream()
                        .filter(project -> Objects.nonNull(project.getParentUid()))
                        .filter(project -> Objects.equals(project.getParentUid(), projectStoryId))
                        .collect(Collectors.toList());
                if (related.size() == 1) {
                            p.setStartDate(related.get(0).getStartDate());
                            p.setEndDate(related.get(0).getEndDate());
                            return saveProject(p);
                } else if (related.size() == 0) {
                    return null;
                }
            } else
                return null;
            return p;
        }

        private List<ProjectStory> findRelatedSubprojectsOrTasks(String Uid) {
            return allProjects.stream()
                    .filter(project -> Objects.nonNull(project.getParentUid()))
                    .filter(project -> Objects.equals(project.getParentUid(), Uid))
                    .collect(Collectors.toList());
        }


    public boolean findProjectByParentUid(String parentUid) {
        return allProjects.stream()
                .filter(projectStory -> Objects.equals(projectStory.getParentUid(), parentUid))
                .count() > 0;
    }
}
