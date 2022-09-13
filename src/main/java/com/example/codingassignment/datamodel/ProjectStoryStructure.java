package com.example.codingassignment.datamodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude
public class ProjectStoryStructure {
    @JsonProperty(value = "items")
    private List<ProjectStory> projectStoryList;

    public ProjectStoryStructure(List<ProjectStory> projectStoryList) {
        this.projectStoryList = projectStoryList;
    }

    public ProjectStoryStructure() {
        this.projectStoryList = new ArrayList<>();
    }

    public List<ProjectStory> getProjectStoryList() {
        return projectStoryList;
    }

    public void setProjectStoryList(List<ProjectStory> projectStoryList) {
        this.projectStoryList = projectStoryList;
    }
}
