package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.UserOutputDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserOutputDto useroutputDto = userService.getUserByEmail(username);


        String password = useroutputDto.getPassword();
        String roleName = useroutputDto.getRoleName();
        SimpleGrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(roleName);

        return new org.springframework.security.core.userdetails.User(username, password, Collections.singleton(grantedAuthorities));
    }

}