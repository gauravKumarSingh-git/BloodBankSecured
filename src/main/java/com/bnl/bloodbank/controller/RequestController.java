package com.bnl.bloodbank.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/request")
@Validated
public class RequestController {
    
    @Autowired
    RequestService requestService;

    /**
     * Add request details
     * @param request
     * @return ResponseEntity<String>
     */
    @PostMapping("/addRequest")
    public ResponseEntity<String> addRequest(@Valid @RequestBody Request request){
        return new ResponseEntity<>(requestService.addRequest(request), HttpStatus.CREATED);
    }

    /**
     * Update request status
     * @param requestId
     * @param status
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @PatchMapping("/updateStatus/{requestId}/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> udpateStatus(@PathVariable long requestId ,@PathVariable String status) throws NotPresentException{
        return new ResponseEntity<>(requestService.updateStatus(requestId, status), HttpStatus.OK);
    }

    /**
     * Delete request
     * @param requestId
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @DeleteMapping("/deleteRequest/{requestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable long requestId) throws NotPresentException{
        return new ResponseEntity<>(requestService.deleteRequest(requestId), HttpStatus.OK);
    }

    /**
     * To get all requests present in database
     * @return
     */
    @GetMapping("/getAllRequests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Request>> getAllRequests(){
        return new ResponseEntity<>(requestService.getAllRequests(), HttpStatus.OK);
    }
}
