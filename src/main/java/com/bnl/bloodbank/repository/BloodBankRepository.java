package com.bnl.bloodbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnl.bloodbank.entity.BloodBank;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {

    Optional<BloodBank> findByMobileNumber(long mobileNumber);
    
}
