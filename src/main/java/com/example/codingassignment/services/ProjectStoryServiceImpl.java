package com.example.codingassignment.services;

import com.example.codingassignment.datamodel.ProjectStory;
import com.example.codingassignment.repository.ProjectStoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    @Override
    public ProjectStory findProjectByUid(String Uid) {
        return allProjects
                .stream()
                .filter(projectStory -> projectStory.getUid().equals(Uid))
                .findFirst()
                .get();
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

    @Override
    public ProjectStory calculateDatesForProject(String projectUid) {
        List<ProjectStory> sortedRelatedSubprojects =
                getRelatedSubprojectsAndTasks(allProjects, projectUid);
        sortedRelatedSubprojects = sortByStartDate(sortedRelatedSubprojects);
        List<ProjectStory> sortedByEndRelatedSubprojects = sortByEndDate(sortedRelatedSubprojects);
        ProjectStory theProject = findProjectByUid(projectUid);
        if (sortedRelatedSubprojects.size() > 0 && sortedByEndRelatedSubprojects.size() > 0) {
            theProject.setStartDate(sortedRelatedSubprojects.get(0).getStartDate());
            theProject.setEndDate((sortedRelatedSubprojects.get(0)).getEndDate());
            projectStoryRepository.save(theProject);
        }
        return theProject;
    }

    @Override
    public ProjectStory deleteTaskOrSubproject(String Uid) {
        ProjectStory SubprojectOrTask = findProjectByUid(Uid);
        if (Objects.nonNull(SubprojectOrTask)) {
            // Check if not a leaf / The Newest Task
            List<ProjectStory> relatedSubprojectsAndTasks = getRelatedSubprojectsAndTasks(allProjects, Uid);
            if (relatedSubprojectsAndTasks.size() > 0) {
                relatedSubprojectsAndTasks = sortByStartDate(relatedSubprojectsAndTasks);
                if (relatedSubprojectsAndTasks.get(relatedSubprojectsAndTasks.size() - 1).getUid().equals(Uid)
                && Objects.equals(SubprojectOrTask.getType(), "TYPE")) {
                    // Can't delete latest Task
                    return null;
                } else {
                    projectStoryRepository.delete(SubprojectOrTask);
                    return SubprojectOrTask;
                }
            } else {
                projectStoryRepository.delete(SubprojectOrTask);
                return SubprojectOrTask;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getJsonProjectHierarchy(String Uid) throws JsonProcessingException {
        ProjectStory theProject = findProjectByUid(Uid);
        List<ProjectStory> allDescendants = calculateProjectHierarchy(Uid);
        return IOUtilService.exportProjectHierarchy(theProject, allDescendants);
    }

    private List<ProjectStory> calculateProjectHierarchy(String Uid) {
        ProjectStory theProject = findProjectByUid(Uid);
        List<ProjectStory> result = new ArrayList<>();
        processRecursionForHierarchy(theProject, result);
        return result;
    }

    private void processRecursionForHierarchy(ProjectStory parent, List<ProjectStory> result) {
        for (ProjectStory child : getProjectChildren(parent)) {
            result.add(child);
            processRecursionForHierarchy(child, result);
        }
    }

    private List<ProjectStory> getRelatedSubprojectsAndTasks(List<ProjectStory> projectStories, String projectUid) {
        ProjectStory someProject = findProjectByUid(projectUid);

        return projectStories.stream()
                .filter(projectStory -> Objects.nonNull(projectStory.getUid()))
                .filter(projectStory -> Objects.equals(someProject.getUid(), projectStory.getParentUid()))
                .collect(Collectors.toList());
    }

    private List<ProjectStory> sortByStartDate(List<ProjectStory> projectStories) {
        return projectStories
                .stream()
                .filter(projectStory -> Objects.nonNull(projectStory.getStartDate()))
                .sorted(Comparator.comparing(ProjectStory::getStartDate))
                .collect(Collectors.toList());
    }

    private List<ProjectStory> sortByEndDate(List<ProjectStory> projectStories) {
        return projectStories
                .stream()
                .filter(projectStory -> Objects.nonNull(projectStory.getStartDate()))
                .sorted((p1, p2) -> p2.getEndDate().compareTo(p1.getEndDate()))
                .collect(Collectors.toList());
    }

    private List<ProjectStory> getProjectChildren(ProjectStory parent) {
        return  getAllProjects()
                .stream()
                .filter(c -> Objects.equals(c.getParentUid(), parent.getUid()))
                .collect(Collectors.toList());
    }
}
