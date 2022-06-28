package com.serverless.example.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.example.model.TEProject;
import com.serverless.example.service.TEProjectService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TEProjectService service;

    @Test
    public void testGetAllProjects_forEmptyResult() throws Exception {
        when(service.getAllProjects()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/getProjects"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }
    
    @Test
    public void testGetAllProjects() throws Exception {
        List<TEProject> projectList = new ArrayList<> ();
        projectList.add(new TEProject());

        when(service.getAllProjects()).thenReturn(projectList);

        this.mockMvc.perform(get("/getProjects"))
            .andExpect(status().isOk())
            .andExpect(content().json("[{'projectid':0,'projectName':null,'projectDescription':null,'createdBy':0,'createdOn':null,'updatedBy':0,'updatedOn':null,'deleted':false}]"));
    }

    @Test
    public void testCreateNewProject_CreateNewRecord() throws Exception {
        List<TEProject> projectList = new ArrayList<> ();
        projectList.add(new TEProject());

        when(service.getProjectByProjectName(anyString())).thenReturn(projectList);
        
        mockMvc.perform( MockMvcRequestBuilders.post("/createProject")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(asJsonString(new TEProject(null,null,0,null,0,null,false))))  
        .andExpect(status().isCreated());
    }

    @Test
    public void testCreateNewProject_IfAlreadyExist() throws Exception {
        List<TEProject> projectList = new ArrayList<> ();
        projectList.add(new TEProject());

        when(service.getProjectByProjectName("Project1")).thenReturn(projectList);
        
        mockMvc.perform( MockMvcRequestBuilders.post("/createProject")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(asJsonString(new TEProject("Project1",null,0,null,0,null,false))))  
        .andExpect(status().isAlreadyReported());
    }

    @Test
    public void testCreateNewProject_IfException() throws Exception {
        List<TEProject> projectList = new ArrayList<> ();
        projectList.add(new TEProject());

        when(service.getProjectByProjectName("Project1")).thenReturn(null);
        
        mockMvc.perform( MockMvcRequestBuilders.post("/createProject")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(asJsonString(new TEProject("Project1",null,0,null,0,null,false))))  
        .andExpect(status().isInternalServerError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
