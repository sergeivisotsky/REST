package org.sergei.rest.service;

import org.sergei.rest.repository.UserRepository;
import org.sergei.rest.exceptions.ResourceNotFoundException;
import org.sergei.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ApiUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with this username not found")
                );
        return new ApiUserDetails(user);
    }
}
