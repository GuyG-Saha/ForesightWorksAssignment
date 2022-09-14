package com.example.codingassignment.datamodel;

import java.util.List;

public class ProjectStoryWithChildren {
    private ProjectStory projectStory;
    private List<ProjectStory> children;

    public ProjectStoryWithChildren(ProjectStory projectStory, List<ProjectStory> children) {
        this.projectStory = projectStory;
        this.children = children;
    }

    public ProjectStoryWithChildren() {
    }

    public ProjectStory getProjectStory() {
        return projectStory;
    }

    public void setProjectStory(ProjectStory projectStory) {
        this.projectStory = projectStory;
    }

    public List<ProjectStory> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectStory> children) {
        this.children = children;
    }
}
