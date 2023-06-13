package com.bnl.bloodbank.service;

import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;

public interface BloodGroupService {

    /**
     * Add new blood group details
     * @param bloodGroup
     * @return String
     * @throws AlreadyPresentException
     */
    public String addBloodGroup(BloodGroup bloodGroup) throws AlreadyPresentException;

    /**
     * Get blood group details based on bloodGroupId
     * @param bloodGroupId
     * @return BloodGroup
     * @throws NotPresentException
     */
    public BloodGroup getBloodGroup(long bloodGroupId) throws NotPresentException;

    /**
     * Delete blood group details by bloodGroupId
     * @param bloodGroupId
     * @return String
     * @throws NotPresentException
     */
    public String deleteBloodGroup(long bloodGroupId) throws NotPresentException;

    /**
     * Update bloodGroup quantity using bloodGroup and quantity to be updated
     * @param bloodGroup
     * @param quantity
     * @return String
     * @throws NotPresentException
     */
    public String updateQuantity(String bloodGroup, long quantity) throws NotPresentException;

    /**
     * Get bloodGroup details by bloodGroup
     * @param bloodGroup
     * @return BloodGroup
     * @throws NotPresentException
     */
    public BloodGroup getByBloodGroup(String bloodGroup) throws NotPresentException;
    
}
