package org.sergei.rest.service;

import org.sergei.rest.repository.UserRepository;
import org.sergei.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ApiUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

   /*@PostConstruct
    private void saveDefaultUser() {
        userRepository.save(
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
