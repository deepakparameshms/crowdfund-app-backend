package com.auth.jwtserver.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.mongodb.core.mapping.DBRef;

@Document(collection = "project")
@Getter
@Setter
public class Project {

    @Id
    private String id;

    @DBRef
    private User founder;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    private boolean isVerified = true;

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

    private double currentAmount = 0;    
    
    private double donations = 0;
    
    private boolean isAchieved = false;

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }
}
