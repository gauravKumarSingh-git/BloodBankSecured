package com.bnl.bloodbank.serviceImpl;

import java.util.Optional;

import com.bnl.bloodbank.service.BloodGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.BloodGroupRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BloodGroupServiceImpl implements BloodGroupService {
    
    @Autowired
    BloodGroupRepository bloodGroupRepository;

    @Override
    public String addBloodGroup(BloodGroup bloodGroup) throws AlreadyPresentException {
        bloodGroupRepository.save(bloodGroup);
        return "Blood Group " + bloodGroup.getBloodGroup() + " successfully saved";
    }

    @Override
    public BloodGroup getBloodGroup(long bloodGroupId) throws NotPresentException {
        return bloodGroupRepository.findById(bloodGroupId).orElseThrow(() -> new NotPresentException("Blood Group with ID : " + bloodGroupId + " not present"));
    }

    @Override
    public String deleteBloodGroup(long bloodGroupId) throws NotPresentException {
        BloodGroup bloodGroupfromRepo = getBloodGroup(bloodGroupId);
        bloodGroupRepository.delete(bloodGroupfromRepo);
        return "Blood Group with ID : " + bloodGroupId + " successfully deleted"; 
    }

    @Override
    public String updateQuantity(String bloodGroup, long quantity) throws NotPresentException {
        BloodGroup bloodGroupFromRepo = getByBloodGroup(bloodGroup);
        bloodGroupFromRepo.setQuantity(quantity);
        return "Successfully updated quantity for Blood Group " + bloodGroup;
    }

    @Override
    public BloodGroup getByBloodGroup(String bloodGroup) throws NotPresentException {
        Optional<BloodGroup> bloodGroupfromRepo = bloodGroupRepository.findByBloodGroup(bloodGroup);
        return bloodGroupfromRepo.orElseThrow(()-> new NotPresentException("Blood Group " + bloodGroup + " not present"));
    }

}
