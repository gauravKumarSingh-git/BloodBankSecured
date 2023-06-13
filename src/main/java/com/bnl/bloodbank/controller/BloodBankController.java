package com.bnl.bloodbank.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnl.bloodbank.entity.BloodBank;
import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.service.BloodBankService;

@RestController
@RequestMapping("/bloodbank")
@Validated
public class BloodBankController {
    
    @Autowired
    BloodBankService bloodBankService;

    /**
     * add new blood bank details
     * @param bloodBank
     * @return ResponseEntity<String>
     * @throws AlreadyPresentException
     */
    @PostMapping("/addBloodBank")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addBloodBank(@Valid @RequestBody BloodBank bloodBank) throws AlreadyPresentException{
        return new ResponseEntity<>(bloodBankService.addBloodBank(bloodBank), HttpStatus.CREATED);
    }

    /**
     * Get details of blood bank using bloodBankId
     * @param bloodBankId
     * @return ResponseEntity<BloodBank>
     * @throws NotPresentException
     */
    @GetMapping("/getBloodBank/{bloodBankId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BloodBank> getBloodBank(@PathVariable long bloodBankId) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.getBloodBank(bloodBankId), HttpStatus.OK);
    }

    /**
     * Get blood groups associated with blood bank
     * @param bloodBankId
     * @return ResponseEntity<List<BloodBank>>
     * @throws NotPresentException
     */
    @GetMapping("/getBloodGroups/{bloodBankId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BloodGroup>> getBloodGroups(@PathVariable long bloodBankId) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.getBloodGroups(bloodBankId), HttpStatus.OK);
    }

    /**
     * update blood bank details
     * @param bloodBank
     * @return ResponseEntity<String>
     * @throws NotPresentException
     * @throws AlreadyPresentException
     */
    @PatchMapping("/updateBloodBank")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateBloodBank(@Valid @RequestBody BloodBank bloodBank) throws NotPresentException, AlreadyPresentException{
        return new ResponseEntity<>(bloodBankService.updateBloodBank(bloodBank), HttpStatus.OK);
    }

    /**
     * Add blood group to blood bank
     * @param bloodBankId
     * @param bloodGroup
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @PatchMapping("/addBloodGroup/{bloodBankId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addBloodGroup(@PathVariable long bloodBankId,@Valid @RequestBody BloodGroup bloodGroup) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.addBloodGroup(bloodBankId, bloodGroup), HttpStatus.OK);
    }

    /**
     * Delete blood bank by bloodBankId
     * @param bloodBankId
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @DeleteMapping("/deleteBloodBank/{bloodBankId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBloodBank(@PathVariable long bloodBankId) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.deleteBloodBank(bloodBankId), HttpStatus.OK);
    }

}
