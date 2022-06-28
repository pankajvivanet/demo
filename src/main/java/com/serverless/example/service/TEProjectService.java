package com.serverless.example.service;

import org.springframework.stereotype.Service;

import com.serverless.example.dao.TEProjectRepository;
import com.serverless.example.model.TEProject;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TEProjectService {
    
    @Autowired
    TEProjectRepository teProjectRepository;

    public Iterable<TEProject> getAllProjects() {
        Iterable<TEProject> allProjects = teProjectRepository.findAll();

        if (((List<TEProject>) allProjects).isEmpty()) {
            return Collections.emptyList();
        }
        return allProjects;
    }

    public TEProject createProject(TEProject project) {
        return teProjectRepository.save(project);
    }

    public List<TEProject> getProjectByProjectName(String projectName) {
        return teProjectRepository.findByProjectName(projectName);
    }

}

