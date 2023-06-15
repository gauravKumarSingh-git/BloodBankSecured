package com.bnl.bloodbank.service;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.entity.Users;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.dto.UserRequestsResponse;

import java.util.List;

public interface UsersService {
    /**
          * To add a new donor. The donor should not have username or password already present in
          * database otherwise AlreadyPresentException will be thrown.
          * @param user
          * @return String
          * @throws AlreadyPresentException
          */
    public String registerUser(Users user) throws AlreadyPresentException;

    /**
     * To update details of donor except username and donorId. The updated phone number should not
     * be already registered
     * @param user
     * @return String
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    public String updateUser(Users user) throws UsernameNotFoundException, AlreadyPresentException;

    /**
     * To add a request to list of request of a donor. Default value of status is pending and
     * date is current date.
     * @param userId
     * @param request
     * @return String
     * @throws NotPresentException
     */
    public String addRequest(long userId, Request request) throws NotPresentException;

    /**
     * Delete a donor by userId
     * @param userId
     * @return String
     * @throws NotPresentException
     */
    public String deleteUser(long userId) throws NotPresentException;

    /**
     * find a donor by username
     * @param username
     * @return Donor
     * @throws UsernameNotFoundException
     */
    public Users findByUsername(String username) throws UsernameNotFoundException;

    /**
     * Find User by user ID
     * @param userId
     * @return
     * @throws NotPresentException
     */
    public Users findByUserId(long userId) throws NotPresentException;

    /**
     * get all requests made by a donor by userId
     * @param userId
     * @return List<Request>
     * @throws NotPresentException
     */
    public List<Request> getRequests(long userId) throws NotPresentException;

    /**
     * Get all pending requests made by a donor by userId
     * @param userId
     * @return List<Reqeusts>
     * @throws NotPresentException
     */
    public List<Request> getPendingRequests(long userId) throws NotPresentException;

    /**
     * Get requests and user details by status of request
     * @return
     */
    List<UserRequestsResponse> getUserAndRequestDetails(String status);

    /**
     * Get list of all users by role
     * @param role
     * @return
     */
    List<Users> getUsersByRole(String role);
}
