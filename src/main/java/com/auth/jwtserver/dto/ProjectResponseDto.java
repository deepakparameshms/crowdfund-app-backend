package com.auth.jwtserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDto {
    
    private String id;
    private String name;
    private boolean isVerified;
    private String description;
    private String category;
    private String vision;
    private String problemStatement;
    private String solution;
    private String website;
    private String email;
    private String instagram;
    private String linkedIn;
    private String logoUrl;
    private double askAmount;
    private double currentAmount;
    private double donations;
    private boolean isAchieved;
    private String founderName;
    private LocationDto location;
}
