package com.auth.jwtserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.auth.jwtserver.document.Transaction;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByUserId(String userId);

    List<Transaction> findByProjectId(String projectId);
}