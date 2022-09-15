package com.example.codingassignment.datamodel;

import java.util.List;

public class ProjectStoryWithAllDescendants {
    private ProjectStory projectStory;
    private List<ProjectStory> allDescendants;

    public ProjectStoryWithAllDescendants(ProjectStory projectStory, List<ProjectStory> allDescendants) {
        this.projectStory = projectStory;
        this.allDescendants = allDescendants;
    }

    public ProjectStoryWithAllDescendants() {
    }

    public ProjectStory getProjectStory() {
        return projectStory;
    }

    public void setProjectStory(ProjectStory projectStory) {
        this.projectStory = projectStory;
    }

    public List<ProjectStory> getAllDescendants() {
        return allDescendants;
    }

    public void setAllDescendants(List<ProjectStory> allDescendants) {
        this.allDescendants = allDescendants;
    }
}
