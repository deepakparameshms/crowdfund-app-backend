package com.auth.jwtserver.service;

import com.auth.jwtserver.document.Project;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.LocationDto;
import com.auth.jwtserver.dto.ProjectDto;
import com.auth.jwtserver.dto.ProjectResponseDto;
import com.auth.jwtserver.exception.BadInputException;
import com.auth.jwtserver.exception.ProjectNotFoundException;
import com.auth.jwtserver.exception.UpdateFailedException;
import com.auth.jwtserver.exception.UserNotFoundException;
import com.auth.jwtserver.repository.ProjectRepository;
import com.auth.jwtserver.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectResponseDto createProject(ProjectDto projectDto, String founderId) {
        
        validateProjectDto(projectDto);
        User founder = userRepository.findById(founderId).orElseThrow(() -> new UserNotFoundException());

        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setCategory(projectDto.getCategory());
        project.setVision(projectDto.getVision());
        project.setProblemStatement(projectDto.getProblemStatement());
        project.setSolution(projectDto.getSolution());
        project.setWebsite(projectDto.getWebsite());
        project.setEmail(projectDto.getEmail());
        project.setInstagram(projectDto.getInstagram());
        project.setLinkedIn(projectDto.getLinkedIn());
        project.setLogoUrl(projectDto.getLogoUrl());
        project.setAskAmount(projectDto.getAskAmount());
        project.setFounder(founder);
        
        LocationDto locationDetails = new LocationDto();
        locationDetails.setCountryName(projectDto.getLocation().getCountryName());
        locationDetails.setCurrency(projectDto.getLocation().getCurrency());
        locationDetails.setCurrencyCode(projectDto.getLocation().getCurrencyCode());
        locationDetails.setFlag(projectDto.getLocation().getFlag());
        project.setLocation(locationDetails);

        try{
            Project savedProject = projectRepository.save(project);
            return convertToResponseDto(savedProject);
        } catch (Exception e){
            throw new UpdateFailedException("Failed to create the Project", e);
        }
    }

    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProjectResponseDto> getUserProjects(String userId) {
        return projectRepository.findByFounderId(userId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public ProjectResponseDto getProjectById(String id) {
        return projectRepository.findById(id)
                .map(this::convertToResponseDto)
                .orElseThrow(() -> new ProjectNotFoundException("Project with Id: " + id + " not found"));
    }

    public ProjectResponseDto updateProject(String id, ProjectDto projectDto) {
        
        validateProjectDto(projectDto);

        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDto.getName());
                    project.setDescription(projectDto.getDescription());
                    project.setCategory(projectDto.getCategory());
                    project.setVision(projectDto.getVision());
                    project.setProblemStatement(projectDto.getProblemStatement());
                    project.setSolution(projectDto.getSolution());
                    project.setWebsite(projectDto.getWebsite());
                    project.setEmail(projectDto.getEmail());
                    project.setInstagram(projectDto.getInstagram());
                    project.setLinkedIn(projectDto.getLinkedIn());
                    project.setLogoUrl(projectDto.getLogoUrl());
                    project.setAskAmount(projectDto.getAskAmount());
                    try{
                        return convertToResponseDto(projectRepository.save(project));
                    } catch (Exception e){
                        throw new UpdateFailedException("Failed to update the project with ID: " +id, e);
                    }
                }).orElseThrow(() -> new ProjectNotFoundException("Project with Id: " + id + " not found"));
    }


    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    private ProjectResponseDto convertToResponseDto(Project project) {
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(project.getId());
        projectResponseDto.setName(project.getName());
        projectResponseDto.setDescription(project.getDescription());
        projectResponseDto.setCategory(project.getCategory());
        projectResponseDto.setVision(project.getVision());
        projectResponseDto.setProblemStatement(project.getProblemStatement());
        projectResponseDto.setSolution(project.getSolution());
        projectResponseDto.setWebsite(project.getWebsite());
        projectResponseDto.setEmail(project.getEmail());
        projectResponseDto.setInstagram(project.getInstagram());
        projectResponseDto.setLinkedIn(project.getLinkedIn());
        projectResponseDto.setLogoUrl(project.getLogoUrl());
        projectResponseDto.setAskAmount(project.getAskAmount());
        projectResponseDto.setCurrentAmount(project.getCurrentAmount());
        projectResponseDto.setDonations(project.getDonations());
        projectResponseDto.setAchieved(project.isAchieved());
        projectResponseDto.setFounderName(project.getFounder().getUsername());

        LocationDto locationDetails = new LocationDto();
        locationDetails.setCountryName(project.getLocation().getCountryName());
        locationDetails.setCurrency(project.getLocation().getCurrency());
        locationDetails.setCurrencyCode(project.getLocation().getCurrencyCode());
        locationDetails.setFlag(project.getLocation().getFlag());

        projectResponseDto.setLocation(locationDetails);
        return projectResponseDto;
    }

    private void validateProjectDto(ProjectDto projectDto) {
        if (projectDto.getName() == null || projectDto.getName().isEmpty()) {
            throw new BadInputException("Project name cannot be null or empty");
        }
        
        if (projectDto.getDescription() == null || projectDto.getDescription().isEmpty()) {
            throw new BadInputException("Project description cannot be null or empty");
        }

        if (projectDto.getCategory() == null || projectDto.getCategory().isEmpty()) {
            throw new BadInputException("Project category cannot be null or empty");
        }

        if (projectDto.getVision() == null || projectDto.getVision().isEmpty()) {
            throw new BadInputException("Project vision cannot be null or empty");
        }

        if (projectDto.getProblemStatement() == null || projectDto.getProblemStatement().isEmpty()) {
            throw new BadInputException("Project problem statement cannot be null or empty");
        }

        if (projectDto.getSolution() == null || projectDto.getSolution().isEmpty()) {
            throw new BadInputException("Project solution cannot be null or empty");
        }

        if (projectDto.getWebsite() == null || projectDto.getWebsite().isEmpty()) {
            throw new BadInputException("Project website cannot be null or empty");
        }

        if (projectDto.getEmail() == null || projectDto.getEmail().isEmpty()) {
            throw new BadInputException("Project email cannot be null or empty");
        }

        if (projectDto.getLogoUrl() == null || projectDto.getLogoUrl().isEmpty()) {
            throw new BadInputException("Project logo URL cannot be null or empty");
        }

        if (projectDto.getLocation() == null) {
            throw new BadInputException("Project location details are missing");
        }

        if (projectDto.getAskAmount() <= 10) {
            throw new BadInputException("Project ask amount should be atlease 10");
        }

    }
}
