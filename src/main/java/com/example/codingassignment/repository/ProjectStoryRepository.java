package com.example.codingassignment.repository;

import com.example.codingassignment.datamodel.ProjectStory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStoryRepository extends CrudRepository<ProjectStory, String> {
}
