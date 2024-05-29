package com.example.api.repository;

import com.example.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations and custom queries.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the found user, or {@link Optional#empty()} if no user was found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their name.
     *
     * @param name the name to search for
     * @return the found user, or {@code null} if no user was found
     */
    User findByName(String name);

}