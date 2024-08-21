package com.auth.jwtserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.utility.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    
    @Operation(summary = "Create New Project")
    @PostMapping("/")
    public ResponseEntity<Object> createNewProject(){
        
        return ResponseBuilder.build(null, null, "Project Successfully Created", getClass());
    }
    
    @Operation(summary = "Get All Projects")
    @GetMapping("/all")
    public ResponseEntity<Object> getAllProjects(){
        
        return ResponseBuilder.build(null, null, "All Projects", getClass());
    }

    @Operation(summary = "Get User's Projects")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserProjects(){
        
        return ResponseBuilder.build(null, null, "User's Projects", getClass());
    }

    @Operation(summary = "Get Project By Id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(){
        
        return ResponseBuilder.build(null, null, "Project By Id", getClass());
    }

    @Operation(summary = "Update Project")
    @PostMapping("/{id}")
    public ResponseEntity<Object> updateProject(){
        
        return ResponseBuilder.build(null, null, "Project Updated", getClass());
    }

    @Operation(summary = "Delete Project")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(){
        
        return ResponseBuilder.build(null, null, "Project Deleted", getClass());
    }


}   
