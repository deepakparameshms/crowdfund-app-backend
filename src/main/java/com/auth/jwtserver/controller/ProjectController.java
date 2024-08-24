package com.auth.jwtserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.ProjectDto;
import com.auth.jwtserver.dto.ProjectResponseDto;
import com.auth.jwtserver.service.ProjectService;
import com.auth.jwtserver.utility.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @Operation(summary = "Create New Project")
    @PostMapping("/")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> createNewProject(@AuthenticationPrincipal User user, @RequestBody ProjectDto projectDto, @RequestParam String founderId){
        ProjectResponseDto projectResponseDto = projectService.createProject(projectDto, founderId);
        return ResponseBuilder.build(HttpStatus.CREATED, null, "Project Successfully Created", projectResponseDto);
    }
    
    @Operation(summary = "Get All Projects")
    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> getAllProjects(@AuthenticationPrincipal User user){
        List<ProjectResponseDto> projectResponseDtoList = projectService.getAllProjects();
        return ResponseBuilder.build(HttpStatus.OK, null, "All Projects Fetched Successfully", projectResponseDtoList);
    }

    @Operation(summary = "Get an User's Projects by userId")
    @GetMapping("/user/{userId}")
    @PreAuthorize("#user.id == #userId")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> getUserProjects(@AuthenticationPrincipal User user,@PathVariable String userId){
        List<ProjectResponseDto> projectResponseDtoList = projectService.getUserProjects(userId);
        return ResponseBuilder.build(HttpStatus.OK, null, "User's Projects", projectResponseDtoList);
    }

    @Operation(summary = "Get Project By Id")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> getProjectById(@AuthenticationPrincipal User user, @PathVariable String id){
        ProjectResponseDto project = projectService.getProjectById(id);
        return ResponseBuilder.build(HttpStatus.OK, null, "Project By Id", project);
    }

    @Operation(summary = "Update Project")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> updateProject(@PathVariable String id, @RequestBody ProjectDto projectDto){
        ProjectResponseDto updatedProject = projectService.updateProject(id, projectDto);
        return ResponseBuilder.build(HttpStatus.OK, null, "Project Updated", updatedProject);
    }

    @Operation(summary = "Delete Project")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> deleteProject(@PathVariable String id){
        projectService.deleteProject(id);
        return ResponseBuilder.build(HttpStatus.OK, null, "Project Deleted", getClass());
    }


}   
