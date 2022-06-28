package com.serverless.example.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.serverless.example.model.TEProject;
import com.serverless.example.model.User;
import com.serverless.example.service.TEProjectService;

@RestController
public class UserController {

    @Autowired
	TEProjectService teProjectService;

    @GetMapping("/users")
    public List<User> lisUsers() {
        return Arrays.asList(new User("John", 20), new User("Mike", 25));
    }
    
    @GetMapping("/getProjects")
	public Iterable<TEProject> getAllProjects() {
		Iterable<TEProject> listOfProject = teProjectService.getAllProjects();
		return listOfProject;
	}

    @PostMapping("/createProject")
	public ResponseEntity<TEProject> createNewProject(@Validated @RequestBody TEProject project) {
		try {
			List<TEProject> projectList = teProjectService.getProjectByProjectName(project.getProjectName());
			if(projectList.size() == 0 ) {
				TEProject newProject = teProjectService.createProject(project);
				return new ResponseEntity<>(newProject, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
}
