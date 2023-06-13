package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.BloodBank;
import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;

public interface BloodBankService {

    /**
     * To add new blood bank details. Mobile number should be unique
     * @param bloodBank
     * @return String
     * @throws AlreadyPresentException
     */
    public String addBloodBank(BloodBank bloodBank) throws AlreadyPresentException;

    /**
     * To get blood bank details based on bloodBankId. Blood bank Id should be present in database.
     * @param bloodBankId
     * @return BloodBank
     * @throws NotPresentException
     */
    public BloodBank getBloodBank(long bloodBankId) throws NotPresentException;

    /**
     * To get blood groups associated with blood bank using bloodBankId
     * @param bloodBankId
     * @return List<BloodGroup>
     * @throws NotPresentException
     */
    public List<BloodGroup> getBloodGroups(long bloodBankId) throws NotPresentException;

    /**
     * To update bloodBank details. Mobile number should be unique for each blood bank
     * @param bloodBank
     * @return String
     * @throws NotPresentException
     * @throws AlreadyPresentException
     */
    public String updateBloodBank(BloodBank bloodBank) throws NotPresentException, AlreadyPresentException;

    /**
     * To add new BloodGroup of a bloodBank. Takes bloodBankId and bloodGroup to be added
     * @param bloodBankId
     * @param bloodGroup
     * @return String
     * @throws NotPresentException
     */
    public String addBloodGroup(long bloodBankId, BloodGroup bloodGroup) throws NotPresentException;

    /**
     * Delete blood bank details based on bloodBankId
     * @param bloodBankId
     * @return String
     * @throws NotPresentException
     */
    public String deleteBloodBank(long bloodBankId) throws NotPresentException;
    
}
