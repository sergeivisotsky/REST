package org.sergei.rest.controller;

import org.sergei.rest.model.User;
import org.sergei.rest.model.UserRoles;
import org.sergei.rest.service.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiIgnore
@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping(value = "/users",
        produces = {"application/json", "application/xml"})
public class ApiUserController {

    @Autowired
    private ApiUserService apiUSerService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(apiUSerService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUserRoles(Collections.singletonList(new UserRoles("USER")));
        User newUser = apiUSerService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
