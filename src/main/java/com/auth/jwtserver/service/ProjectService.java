package com.auth.jwtserver.service;

import com.auth.jwtserver.document.Project;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.ProjectDto;
import com.auth.jwtserver.dto.ProjectResponseDto;
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
        
        User founder = userRepository.findById(founderId).orElseThrow(() -> new RuntimeException("Founder not found"));

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
        
        Project savedProject = projectRepository.save(project);
        return convertToDto(savedProject);
    }

    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProjectResponseDto> getUserProjects(String userId) {
        return projectRepository.findByFounderId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProjectResponseDto getProjectById(String id) {
        return projectRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public ProjectResponseDto updateProject(String id, ProjectDto projectDto) {
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
                    return convertToDto(projectRepository.save(project));
                }).orElse(null);
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    private ProjectResponseDto convertToDto(Project project) {
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
        projectResponseDto.setAsk(project.getAskAmount());
        projectResponseDto.setCurrent(project.getCurrentAmount());
        projectResponseDto.setDonations(project.getDonations());
        projectResponseDto.setAchieved(project.isAchieved());
        return projectResponseDto;
    }
}
