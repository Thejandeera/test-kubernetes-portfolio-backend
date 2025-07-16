package com.web.portfolio.UserManagement.Repository;

import com.web.portfolio.UserManagement.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find user by username
     * @param userName the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUserName(String userName);

    /**
     * Find user by email
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if username already exists
     * @param userName the username to check
     * @return true if username exists, false otherwise
     */
    boolean existsByUserName(String userName);

    /**
     * Check if email already exists
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);
}