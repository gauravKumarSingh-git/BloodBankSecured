package com.bnl.bloodbank.repository;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByPhoneNumber(long phoneNumber);

    @Query("SELECT d.requests from Users d where d.userId = :userId")
    List<Request> findRequestsByUserId(long userId);

    @Query("SELECT r from Users d inner join Request r on d.userId = r.user.userId where d.userId = :userId and r.status = 'pending'")
    List<Request> findPendingRequestsByUserId(long userId);

    @Query("SELECT d from Users d inner join Request r on d.userId = r.user.userId")
    List<Users> getUserAndRequestDetails();

    List<Users> findByRole(String role);
}
