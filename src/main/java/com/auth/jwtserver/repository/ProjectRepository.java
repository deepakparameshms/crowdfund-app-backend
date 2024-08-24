package com.auth.jwtserver.repository;

import com.auth.jwtserver.document.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findByFounderId(String founderId);
}