package com.bnl.bloodbank.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.service.BloodGroupService;
import com.bnl.bloodbank.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.RequestRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    BloodGroupService bloodGroupService;

    @Override
    public String addRequest(Request request) {
        request.setDate(LocalDate.now());
        request.setStatus("Pending");
        requestRepository.save(request);
        return "Request saved successfully";
    }

    @Override
    public String updateStatus(long requestId, String status) throws NotPresentException {
        Optional<Request> requestFromRepo = requestRepository.findById(requestId);
        Request request = requestFromRepo.orElseThrow(() -> new NotPresentException("Request for id " + requestId + " not found"));
//        if(!request.getStatus().equalsIgnoreCase(status) && request.getStatus().equalsIgnoreCase("pending")){
//            BloodGroup bloodGroup = bloodGroupService.getByBloodGroup(request.getBloodGroup());
//            if(bloodGroup.getQuantity() <= request.getQuantity()){
//                throw new NotPresentException("Not enough quantity present in blood bank");
//            }
//            bloodGroupService.updateQuantity(request.getBloodGroup(), bloodGroup.getQuantity() - request.getQuantity());
//            request.setStatus(status);
//        }

        request.setStatus(status);
        return "Status for Request ID " + requestId + " successfully updated";
    }

    @Override
    public String deleteRequest(long requestId) throws NotPresentException {
        Optional<Request> requestFromRepo = requestRepository.findById(requestId);
        Request request = requestFromRepo.orElseThrow(() -> new NotPresentException("Request for id " + requestId + " not found"));
        requestRepository.delete(request);
        return "Request with ID " + requestId + " successfully deleted";
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

}
