package com.auth.jwtserver.service.interfaces;

import com.auth.jwtserver.dto.ProjectDto;
import com.auth.jwtserver.dto.ProjectResponseDto;
import com.auth.jwtserver.exception.BadInputException;
import com.auth.jwtserver.exception.ProjectNotFoundException;
import com.auth.jwtserver.exception.UpdateFailedException;
import com.auth.jwtserver.exception.UserNotFoundException;

import java.util.List;

public interface IProjectService {

    ProjectResponseDto createProject(ProjectDto projectDto, String founderId) 
        throws UserNotFoundException, UpdateFailedException, BadInputException;

    List<ProjectResponseDto> getAllProjects();

    List<ProjectResponseDto> getUserProjects(String userId);

    ProjectResponseDto getProjectById(String id) 
        throws ProjectNotFoundException;

    ProjectResponseDto updateProject(String id, ProjectDto projectDto) 
        throws ProjectNotFoundException, UpdateFailedException, BadInputException;

    void deleteProject(String id);
}
