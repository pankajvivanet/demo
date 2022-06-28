package com.serverless.example.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.serverless.example.model.TEProject;

@Repository
public interface TEProjectRepository extends CrudRepository <TEProject, Integer >{
    
    public List<TEProject> findByProjectName(String projectName);

    // @Query("SELECT p from te_projects p where p.projectName = :projectName and p.createdBy = :createdBy")
    // public List<TEProject> listProjectWithNameAndCreatedBy(@Param("projectName") String projectName, @Param("createdBy") int createdBy);
}

