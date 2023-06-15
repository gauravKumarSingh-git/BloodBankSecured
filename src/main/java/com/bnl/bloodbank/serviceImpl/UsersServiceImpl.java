package com.bnl.bloodbank.serviceImpl;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.entity.Users;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.UsersRepository;
import com.bnl.bloodbank.service.UsersService;
import com.bnl.bloodbank.dto.UserRequestsResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(Users user) throws AlreadyPresentException {
        if(isUsernamePresent(user.getUsername())){
            throw new AlreadyPresentException("Username alredy present");
        }
        if(isPhoneNumberPresent(user.getPhoneNumber())){
            throw new AlreadyPresentException("Phone number already present");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Users with username : " + user.getUsername() + " successfully registered";
    }

    @Override
    public String updateUser(Users user) throws UsernameNotFoundException, AlreadyPresentException {
        Users userFromRepo = findByUsername(user.getUsername());
        if(isPhoneNumberPresent(user.getPhoneNumber())){
            if(userFromRepo.getPhoneNumber() != user.getPhoneNumber()){
                throw new AlreadyPresentException("Phone Number already present");
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "Successfully updated";
    }

    @Override
    public String addRequest(long userId, Request request) throws NotPresentException {
        Users userFromRepo = findByUserId(userId);
        if(request.getStatus() == null){
            request.setStatus("pending");
        }
        if(request.getDate() == null){
            request.setDate(LocalDate.now());
        }
        request.setUser(userFromRepo);
        List<Request> requests = userFromRepo.getRequests();
        requests.add(request);
        userFromRepo.setRequests(requests);
        return "Request Successfully added to user with ID: " + userId;
    }


    @Override
    public String deleteUser(long userId) throws NotPresentException {
        Users userFromRepo = findByUserId(userId);
        userRepository.delete(userFromRepo);
        return "User with userID: " + userId + " successfully deleted";
    }


    @Override
    public Users findByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userFromRepo = userRepository.findByUsername(username);
        return userFromRepo.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    @Override
    public Users findByUserId(long userId) throws NotPresentException {
        Optional<Users> userFromRepo = userRepository.findById(userId);
        return userFromRepo.orElseThrow(() -> new NotPresentException("User ID: " + userId + " not found"));
    }

    @Override
    public List<Request> getRequests(long userId) throws NotPresentException {
        if(!isUserPresent(userId)) throw new NotPresentException("user with userID: " + userId + " not found");
        return userRepository.findRequestsByUserId(userId);
    }


    @Override
    public List<Request> getPendingRequests(long userId) throws NotPresentException {
        if(!isUserPresent(userId)) throw new NotPresentException("user with userID: " + userId + " not found");
        return userRepository.findPendingRequestsByUserId(userId);
    }

    @Override
    public List<UserRequestsResponse> getUserAndRequestDetails(String status) {
        List<Users> response = userRepository.getUserAndRequestDetails();
        List<UserRequestsResponse> ret = new ArrayList<>();
        response.forEach(user -> user.getRequests().stream()
                .filter(req -> req.getStatus().equalsIgnoreCase(status))
                .forEach(req-> {
                    UserRequestsResponse userReqRes = new UserRequestsResponse(user.getUsername(), user.getEmail(), user.getState(), user.getCity(), user.getAddress(), user.getPhoneNumber(), req.getBloodGroup(), req.getQuantity(), req.getDate(), req.getStatus());
                    ret.add(userReqRes);
                }));
        return ret;
    }

    @Override
    public List<Users> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    private boolean isUsernamePresent(String username){
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean isUserPresent(long userId){
        return userRepository.findById(userId).isPresent();
    }

    private boolean isPhoneNumberPresent(long phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }
}
