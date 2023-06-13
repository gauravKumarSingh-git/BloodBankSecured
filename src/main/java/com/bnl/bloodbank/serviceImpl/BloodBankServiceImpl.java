package com.bnl.bloodbank.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bnl.bloodbank.service.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.BloodBank;
import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.BloodBankRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BloodBankServiceImpl implements BloodBankService {

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Override
    public String addBloodBank(BloodBank bloodBank) throws AlreadyPresentException {
        if(isMobileNumberPresent(bloodBank.getMobileNumber())){
            throw new AlreadyPresentException("Mobile number already present");
        }
        bloodBank.setLastUpdated(LocalDate.now());
        bloodBankRepository.save(bloodBank);
        return "Blood Bank " + bloodBank.getName() + " successfully saved";
    }

    @Override
    public BloodBank getBloodBank(long bloodBankId) throws NotPresentException {
        Optional<BloodBank> bloodBankfromRepo = bloodBankRepository.findById(bloodBankId);
        return bloodBankfromRepo.orElseThrow(() -> new NotPresentException("Blood Bank with given ID not present"));
    }

    @Override
    public List<BloodGroup> getBloodGroups(long bloodBankId) throws NotPresentException {
        BloodBank bloodBank = getBloodBank(bloodBankId);
        return bloodBank.getBloodgroups();
    }

    @Override
    public String updateBloodBank(BloodBank bloodBank) throws NotPresentException, AlreadyPresentException {
        BloodBank bloodBankfromRepo = getBloodBank(bloodBank.getBloodBankId());
        if(isMobileNumberPresent(bloodBank.getMobileNumber())){
            if(bloodBankfromRepo.getMobileNumber() != bloodBank.getMobileNumber()){
                throw new AlreadyPresentException("Mobile Number already present");
            }
        }
        bloodBankRepository.save(bloodBank);

        return "Blood Bank " + bloodBank.getName() + " successfully updated";
    }

    @Override
    public String addBloodGroup(long bloodBankId, BloodGroup bloodGroup) throws NotPresentException {
        BloodBank bloodBankfromRepo = getBloodBank(bloodBankId);
        bloodBankfromRepo.setLastUpdated(LocalDate.now());
        bloodGroup.setBloodBank(bloodBankfromRepo);
        List<BloodGroup> bloodGroups = bloodBankfromRepo.getBloodgroups();
        bloodGroups.add(bloodGroup);
        bloodBankfromRepo.setBloodgroups(bloodGroups);
        return "Blood Group successfully added to blood bank : " + bloodBankfromRepo.getName();
    }

    @Override
    public String deleteBloodBank(long bloodBankId) throws NotPresentException {
        BloodBank bloodBankfromRepo = getBloodBank(bloodBankId);
        bloodBankRepository.delete(bloodBankfromRepo);
        return "Blood bank with ID : " + bloodBankId + " successfully deleted";
    }

    private boolean isMobileNumberPresent(long mobileNumber){
        if(bloodBankRepository.findByMobileNumber(mobileNumber).isEmpty()){
            return false;
        }
        return true;
    }
    
}
