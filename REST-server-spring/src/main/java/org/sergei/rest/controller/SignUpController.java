/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.controller;

import org.sergei.rest.model.User;
import org.sergei.rest.model.UserRoles;
import org.sergei.rest.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiIgnore
@RestController
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUserRoles(Collections.singletonList(new UserRoles("USER")));
        User newUser = signUpService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
