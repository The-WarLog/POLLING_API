package com.polling_system.Polling_System.services.impl;

import com.polling_system.Polling_System.models.User;
import com.polling_system.Polling_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional // Ensures roles are fetched before the session closes (due to FetchType.LAZY)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        // The user's roles are already GrantedAuthority instances due to the change in Roles.java
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>)(Set<?>) user.getRoles();

        // You must return an object of type org.springframework.security.core.userdetails.User
        // The original code had a compilation error here.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}