package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.UserOutputDto;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public CustomUserDetailsService( UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        String password = user.getPassword();
        String roleName = user.getRole().getRoleName();
        SimpleGrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(roleName);

        return new org.springframework.security.core.userdetails.User(username, password, Collections.singleton(grantedAuthorities));
    }

}