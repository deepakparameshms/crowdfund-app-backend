package com.auth.jwtserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 5000)
    private String description;

    @NotBlank
    private String category;

    @NotBlank
    @Size(min = 3, max = 5000)
    private String vision;

    @NotBlank
    @Size(min = 3, max = 5000)
    private String problemStatement;

    @NotBlank
    @Size(min = 3, max = 5000)
    private String solution;

    @NotBlank
    private String website;

    @NotBlank
    @Email
    private String email;

    private String instagram;
    private String linkedIn;
    
    @NotBlank
    private String logoUrl;

    @NotBlank
    private double askAmount;
    
    @NotBlank
    private double currentAmount;

    private LocationDto location;
}
