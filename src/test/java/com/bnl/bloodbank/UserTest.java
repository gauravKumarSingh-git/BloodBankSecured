package com.bnl.bloodbank;

import com.bnl.bloodbank.dto.UserRequestsResponse;
import com.bnl.bloodbank.entity.Users;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.UsersRepository;
import com.bnl.bloodbank.service.UsersService;
import com.bnl.bloodbank.serviceImpl.UsersServiceImpl;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserTest {

    @Mock
    UsersRepository usersRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UsersService userService = new UsersServiceImpl();

        private Users user = Users.builder()
                    .userId(1)
                    .address("DTP")
                    .city("Bangalore")
                    .state("Karnataka")
                    .gender("Male")
                    .email("user@gmail.com")
                    .password("password")
                    .username("user")
                    .phoneNumber(1234567890L)
                    .dateOfBirth(LocalDate.of(2000, 10, 11))
                    .requests( new ArrayList<Request>(Arrays.asList(
                        new Request(1L, "A+", 10L, LocalDate.now(), "pending", this.user )
                    )))
                    .build();

    private static Request request =
            Request.builder()
                    .requestId(1)
                    .bloodGroup("A+")
                    .quantity(2)
                    .build();

    /**
     * To check user registration is successful if username is unique and phone number does not belong to any other user
     * @throws AlreadyPresentException
     */
    @Test
    void validUsersRegistration() throws AlreadyPresentException{
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        Mockito.when(usersRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.empty());
        Assertions.assertEquals("Users with username : " + user.getUsername() + " successfully registered", userService.registerUser(user) );
    }

    /**
     * To check registerUsers throws AlreadyPresentException if username already present
     * @throws AlreadyPresentException
     */
    @Test
    void invalidUsersRegistrtionUsernameAlreadyPresent() throws  AlreadyPresentException{
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        AlreadyPresentException ex = Assertions.assertThrows(
                    AlreadyPresentException.class,
                    () -> userService.registerUser(user)
        );
        Assertions.assertEquals("Username alredy present", ex.getMessage());
    }

    /**
     * To check registerUsers throws AlreadyPresentException if Mobile number is registered with some other user
     * @throws AlreadyPresentException
     */
    @Test
    void invalidUsersRegistrationPhoneNumberAlreadyPresent() throws AlreadyPresentException{
        Mockito.when(usersRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.of(user));
        AlreadyPresentException ex = Assertions.assertThrows(
                AlreadyPresentException.class,
                () -> userService.registerUser(user)
        );
        Assertions.assertEquals("Phone number already present", ex.getMessage());
    }

    /**
     * To check updateUsers is successful if username is valid
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    @Test
    void validUpdateUsers() throws UsernameNotFoundException, AlreadyPresentException {
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Assertions.assertEquals("Successfully updated", userService.updateUser(user));
    }

    /**
     * To check addRequest is successful if username is valid
     * @throws NotPresentException
     */
    @Test
    void validAddRequest() throws NotPresentException {
        Mockito.when(usersRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Assertions.assertEquals("Request Successfully added to user with ID: " + user.getUserId(), userService.addRequest(user.getUserId(), request));
    }

    /**
     * To check deleteUsers is successful if userId is valid
     * @throws NotPresentException
     */
    @Test
    void validUsersDelete() throws NotPresentException {
        Mockito.when(usersRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Assertions.assertEquals("User with userID: " + user.getUserId() + " successfully deleted", userService.deleteUser(user.getUserId()));
    }

    /**
     * To check findByUsername throws UsernameNotPresentException if username is not present in database.
     * @throws UsernameNotFoundException
     */
    @Test
    void invalidFindByUsername() throws UsernameNotFoundException {
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userService.findByUsername(user.getUsername())
        );
        Assertions.assertEquals("Username " + user.getUsername() + " not found", ex.getMessage());
    }

    /**
     * To check findByUserId will throw NotPresentException if UserId is not present in database
     * @throws NotPresentException
     */
    @Test
    void invalidFindByUserId() throws NotPresentException{
        Mockito.when(usersRepository.findById(user.getUserId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> userService.findByUserId(user.getUserId())
        );
        Assertions.assertEquals("User ID: " + user.getUserId() + " not found", ex.getMessage());
    }

    /**
     * To check getRequest is successful if userId is valid
     * @throws NotPresentException
     */
    @Test
    void validGetRequest() throws NotPresentException {
        Mockito.when(usersRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(usersRepository.findRequestsByUserId(user.getUserId())).thenReturn(user.getRequests());
        Assertions.assertEquals(user.getRequests(), userService.getRequests(user.getUserId()));
    }

    /**
     * To test whether getUserAndRequestDetails returns details with status as given in parameter
     */
    @Test
    void validGetUserAndRequestDetails() {
        Mockito.when(usersRepository.getUserAndRequestDetails()).thenReturn(List.of(user));
        List<UserRequestsResponse> res = userService.getUserAndRequestDetails("pending");
        res.forEach(userReq -> {
            Assertions.assertEquals("pending", userReq.getStatus());
        });
    }



}
