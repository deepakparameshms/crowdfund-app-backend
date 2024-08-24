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
    private double ask;
    private double current;
    private double donations;
    private boolean isAchieved;
}