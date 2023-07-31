package com.bnl.bloodbank.controller;

import com.bnl.bloodbank.dto.AuthRequest;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.entity.Users;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.config.JwtService;
import com.bnl.bloodbank.service.UsersService;
import com.bnl.bloodbank.dto.UserRequestsResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * To register a new user
     * @param user
     * @return ResponseEntity<String>
     * @throws AlreadyPresentException
     */
    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@Valid @RequestBody Users user) throws AlreadyPresentException {
        return new ResponseEntity<>(usersService.registerUser(user), HttpStatus.CREATED);
    }

    /**
     * To update user details which are already present in database
     * @param user
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@Valid @RequestBody Users user) throws UsernameNotFoundException, AlreadyPresentException{
        return new ResponseEntity<>(usersService.updateUser(user), HttpStatus.OK);
    }

    /**
     * To add request of a user by userId
     * @param userId
     * @param request
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @PatchMapping("/addRequest/{userId}")
    public ResponseEntity<String> addRequest(@PathVariable long userId,@Valid @RequestBody Request request) throws NotPresentException {
        return new ResponseEntity<>(usersService.addRequest(userId, request), HttpStatus.OK);
    }

    /**
     * To delete user by userId
     * @param userId
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) throws NotPresentException{
        return new ResponseEntity<>(usersService.deleteUser(userId), HttpStatus.OK);
    }

    /**
     * To find a user by username
     * @param username
     * @return ResponseEntity<Users>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<Users> findByUsername(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<Users>(usersService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/isUsernamePresent/{username}")
    public ResponseEntity<Boolean> isUsernamePresent(@PathVariable String username) throws UsernameNotFoundException{
        try{
            usersService.findByUsername(username);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (UsernameNotFoundException e){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    /**
     * To find a user by userId
     * @param userId
     * @return ResponseEntity<Users>
     * @throws NotPresentException
     */
    @GetMapping("/findByUserId/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Users> findByUserId(@PathVariable long userId) throws NotPresentException{
        return new ResponseEntity<Users>(usersService.findByUserId(userId), HttpStatus.OK);
    }

    /**
     * to get Requests made by a user by userId
     * @param userId
     * @return ResponseEntity<List<Request>>
     * @throws NotPresentException
     */
    @GetMapping("/getRequests/{userId}")
    public ResponseEntity<List<Request>> getRequests(@PathVariable long userId) throws NotPresentException{
        return new ResponseEntity<>(usersService.getRequests(userId), HttpStatus.OK);
    }

    /**
     * To get pending requests of a user
     * @param userId
     * @return ResponseEntity<List<Request>>
     * @throws NotPresentException
     */
    @GetMapping("/getPendingRequests/{userId}")
    public ResponseEntity<List<Request>> getPendingRequests(@PathVariable long userId) throws NotPresentException{
        return new ResponseEntity<>(usersService.getPendingRequests(userId), HttpStatus.OK);
    }

    /**
     * To get user and request details by status and role
     * @return List<UserRequestsResponse>
     */
    @GetMapping("/getUserAndRequestDetails/{role}/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRequestsResponse>> getUserAndRequestDetails(@PathVariable String role, @PathVariable String status){
        return new ResponseEntity<>(usersService.getUserAndRequestDetails(role, status), HttpStatus.OK);
    }

    /**
     * To get user and request details by status
     * @return List<UserRequestsResponse>
     */
    @GetMapping("/getUserAndRequestByStatus/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRequestsResponse>> getUserAndRequestByStatus(@PathVariable String status){
        return new ResponseEntity<>(usersService.getUserAndRequestByStatus(status), HttpStatus.OK);
    }

    /**
     * To get details of all users by role
     * @param role
     * @return
     */
    @GetMapping("/getUsersByRole/{role}/{pageNumber}/{sortBy}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<Users>> getDonors(@PathVariable String role, @PathVariable int pageNumber,@PathVariable String sortBy){
        return new ResponseEntity<>(usersService.getUsersByRole(role, pageNumber, sortBy), HttpStatus.OK);
    }

    /**
     * To authenticate the user and return JWT token
     * @param authRequest
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws UsernameNotFoundException {
        Users user = usersService.findByUsername(authRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return new ResponseEntity<>(jwtService.generateToken(authRequest.getUsername(), user.getRole()), HttpStatus.OK);
        }else{
            throw new UsernameNotFoundException("Invalid User Request!");
        }
    }

    @PutMapping("/updatePassword/{username}/{password}")
    public ResponseEntity<String> updatePassword(@PathVariable String username, @PathVariable String password) throws UsernameNotFoundException{
        return new ResponseEntity<>(usersService.updatePassword(username, password), HttpStatus.OK);
    }
}
