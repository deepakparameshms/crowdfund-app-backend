
package com.auth.jwtserver.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.auth.jwtserver.document.Project;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.LocationDto;
import com.auth.jwtserver.dto.ProjectDto;
import com.auth.jwtserver.dto.ProjectResponseDto;
import com.auth.jwtserver.exception.ProjectNotFoundException;
import com.auth.jwtserver.exception.UpdateFailedException;
import com.auth.jwtserver.exception.UserNotFoundException;
import com.auth.jwtserver.repository.ProjectRepository;
import com.auth.jwtserver.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ProjectService projectService;
    private User founder;
    private ProjectDto projectDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Sample founder
        founder = new User("testUser", "test@example.com", "password");
        // Sample ProjectDto
        LocationDto locationDto = new LocationDto();
        locationDto.setCountryName("India");
        locationDto.setCurrency("INR");
        locationDto.setCurrencyCode("INR");
        locationDto.setFlag("flag-url");
        projectDto = new ProjectDto();
        projectDto.setName("Test Project");
        projectDto.setDescription("Test Description");
        projectDto.setCategory("Test Category");
        projectDto.setVision("Test Vision");
        projectDto.setProblemStatement("Test Problem Statement");
        projectDto.setSolution("Test Solution");
        projectDto.setWebsite("https://test.com");
        projectDto.setEmail("test@gmail.com");
        projectDto.setInstagram("https://instagram.com/test");
        projectDto.setLinkedIn("https://linkedin.com/test");
        projectDto.setLogoUrl("https://logo.com");
        projectDto.setAskAmount(1000);
        projectDto.setLocation(locationDto);
    }
    @Test
    void testCreateProject_Success() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(founder));
        when(projectRepository.save(any())).thenReturn(new Project());
        ProjectResponseDto responseDto = projectService.createProject(projectDto, "founderId");
        assertNotNull(responseDto);
        verify(userRepository, times(1)).findById("founderId");
        verify(projectRepository, times(1)).save(any());
    }
    @Test
    void testCreateProject_UserNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> projectService.createProject(projectDto, "invalidFounderId"));
        verify(userRepository, times(1)).findById("invalidFounderId");
        verify(projectRepository, never()).save(any());
    }
    @Test
    void testCreateProject_SaveFails() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(founder));
        when(projectRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(UpdateFailedException.class, () -> projectService.createProject(projectDto, "founderId"));
        verify(userRepository, times(1)).findById("founderId");
        verify(projectRepository, times(1)).save(any());
    }
    @Test
    void testGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        when(projectRepository.findAll()).thenReturn(projects);
        List<ProjectResponseDto> responseDtos = projectService.getAllProjects();
        assertEquals(1, responseDtos.size());
        verify(projectRepository, times(1)).findAll();
    }
    @Test
    void testGetUserProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        when(projectRepository.findByFounderId(anyString())).thenReturn(projects);
        List<ProjectResponseDto> responseDtos = projectService.getUserProjects("founderId");
        assertEquals(1, responseDtos.size());
        verify(projectRepository, times(1)).findByFounderId("founderId");
    }
    @Test
    void testGetProjectById_Success() {
        when(projectRepository.findById(anyString())).thenReturn(Optional.of(new Project()));
        ProjectResponseDto responseDto = projectService.getProjectById("projectId");
        assertNotNull(responseDto);
        verify(projectRepository, times(1)).findById("projectId");
    }
    @Test
    void testGetProjectById_NotFound() {
        when(projectRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById("invalidProjectId"));
        verify(projectRepository, times(1)).findById("invalidProjectId");
    }
    @Test
    void testUpdateProject_Success() {
        when(projectRepository.findById(anyString())).thenReturn(Optional.of(new Project()));
        when(projectRepository.save(any())).thenReturn(new Project());
        ProjectResponseDto responseDto = projectService.updateProject("projectId", projectDto);
        assertNotNull(responseDto);
        verify(projectRepository, times(1)).findById("projectId");
        verify(projectRepository, times(1)).save(any());
    }
    @Test
    void testUpdateProject_NotFound() {
        when(projectRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.updateProject("invalidProjectId", projectDto));
        verify(projectRepository, times(1)).findById("invalidProjectId");
    }
    @Test
    void testDeleteProject() {
        doNothing().when(projectRepository).deleteById(anyString());
        projectService.deleteProject("projectId");
        verify(projectRepository, times(1)).deleteById("projectId");
    }
}
