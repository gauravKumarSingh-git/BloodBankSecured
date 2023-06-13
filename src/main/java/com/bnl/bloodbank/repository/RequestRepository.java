package com.bnl.bloodbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnl.bloodbank.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
    
}
