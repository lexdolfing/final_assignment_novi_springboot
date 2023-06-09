package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.UserInputDto;
import com.novi.DemoDrop.Dto.OutputDto.UserOutputDto;
import com.novi.DemoDrop.exceptions.BadRequestException;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.RoleRepository;
import com.novi.DemoDrop.repositories.UserRepository;
import com.novi.DemoDrop.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserOutputDto> getAllUsers() {
        Iterable<User> allUsers = userRepository.findAll();
        List<UserOutputDto> allUsersDto = new ArrayList<>();

        for (User u : allUsers) {
            UserOutputDto userOutputDto;
            userOutputDto = makeTheDto(u);
            allUsersDto.add(userOutputDto);
        }
        return allUsersDto;
    }

    public UserOutputDto getUserByEmail(String email) {
        new UserOutputDto();
        UserOutputDto userOutputDto;
        if (userRepository.findByEmail(email) == null) throw new RecordNotFoundException();
        User user = userRepository.findByEmail(email);
        userOutputDto = makeTheDto(user);
        return userOutputDto;
    }


    public UserOutputDto makeTheDto(User u) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(u.getId());
        userOutputDto.setEmail(u.getEmail());
        userOutputDto.setRoleName(u.getRole().getRoleName());
        return userOutputDto;
    }

    public User makeUser(UserInputDto userInputDto) {
        User user = new User();
        user.setEmail(userInputDto.getEmail());
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        return user;

    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.deleteById(user.getId());
    }

    public String getAuthorities(String email) {
        if (userRepository.findByEmail(email) == null)
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException(email);
        User user = userRepository.findByEmail(email);
        UserOutputDto userDto = makeTheDto(user);
        return userDto.getRoleName();
    }

    public String createUser(UserInputDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new BadRequestException("user with this email already exists");
        }
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(makeUser(userDto));
        return newUser.getEmail();
    }

    public void addRole(String email, String roleName) {

        if (userRepository.findByEmail(email) == null)
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException(email);
        User user = userRepository.findByEmail(email);
        Role role = new Role();
        if (Objects.equals(roleName, "ROLE_USER")) {
            role.setRoleName("ROLE_USER");
        } else if (Objects.equals(roleName, "ROLE_ADMIN")) {
            role.setRoleName("ROLE_ADMIN");
        }
        role.setUser(user);
        roleRepository.save(role);
        user.setRole(role);
        userRepository.save(user);
    }
}
