/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.repository;

import org.sergei.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u  WHERE u.username = :username")
    Optional<User> findByUserName(@Param("username") String username);
}
