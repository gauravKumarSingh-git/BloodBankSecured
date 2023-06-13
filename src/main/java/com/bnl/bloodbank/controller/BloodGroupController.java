package com.bnl.bloodbank.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.service.BloodGroupService;

@RestController
@RequestMapping("/bloodgroup")
@Validated
public class BloodGroupController {

    @Autowired
    BloodGroupService bloodGroupService;

    /**
     * Add blood group details
     * @param bloodGroup
     * @return ResponseEntity<String>
     * @throws AlreadyPresentException
     */
    @PostMapping("/addBloodGroup")
    public ResponseEntity<String> addBloodGroup(@Valid @RequestBody BloodGroup bloodGroup) throws AlreadyPresentException{
        return new ResponseEntity<>(bloodGroupService.addBloodGroup(bloodGroup), HttpStatus.CREATED);
    }

    /**
     * get blood group details by bloodGroupId
     * @param bloodGroupId
     * @return ResponseEntity<BloodGroup>
     * @throws NotPresentException
     */
    @GetMapping("/getBloodGroup/{bloodGroupId}")
    public ResponseEntity<BloodGroup> getBloodGroup(@PathVariable long bloodGroupId) throws NotPresentException{
        return new ResponseEntity<>(bloodGroupService.getBloodGroup(bloodGroupId), HttpStatus.OK);
    }

    /**
     * Delete blood group details
     * @param bloodGroupId
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @DeleteMapping("/deleteBloodGroup/{bloodGroupId}")
    public ResponseEntity<String> deleteBloodGroup(@PathVariable long bloodGroupId) throws NotPresentException{
        return new ResponseEntity<>(bloodGroupService.deleteBloodGroup(bloodGroupId), HttpStatus.OK);
    }

    /**
     * Update blood group quantity
     * @param bloodGroup
     * @param quantity
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @PatchMapping("/updateQuantity/{bloodGroup}/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable String bloodGroup, @PathVariable long quantity) throws NotPresentException{
        return new ResponseEntity<>(bloodGroupService.updateQuantity(bloodGroup, quantity), HttpStatus.OK);
    }

    /**
     * Get blood group details by bloodGroup
     * @param bloodGroup
     * @return ResponseEntity<BloodGroup>
     * @throws NotPresentException
     */
    @GetMapping("/getByBloodGroup/{bloodGroup}")
    public ResponseEntity<BloodGroup> getByBloodGroup(@PathVariable String bloodGroup) throws NotPresentException{
        return new ResponseEntity<>(bloodGroupService.getByBloodGroup(bloodGroup), HttpStatus.OK);
    }
    
}
