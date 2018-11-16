package org.sergei.rest.service;

import org.sergei.rest.dao.UserDAO;
import org.sergei.rest.model.User;
import org.sergei.rest.model.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ApiUserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiUserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

   /*@PostConstruct
    private void saveDefaultUser() {
        userDAO.save(
                new User("admin",
                        passwordEncoder.encode("123456"),
                        Arrays.asList(
                                new UserRoles("USER"),
                                new UserRoles("ADMIN")
                        )
                )
        );
    }*/
}
