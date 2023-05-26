package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.UserInputDto;
import com.novi.DemoDrop.Dto.OutputDto.UserOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.exceptions.UsernameNotFoundException;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.RoleRepository;
import com.novi.DemoDrop.repositories.UserRepository;
import com.novi.DemoDrop.utils.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserOutputDto> getAllUsers(){
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
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userOutputDto = makeTheDto(user);
        } else {
            throw new UsernameNotFoundException("No user found with this email");
        }
        return userOutputDto;
    }


    public UserOutputDto makeTheDto (User u) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(u.getId());
        userOutputDto.setEmail(u.getEmail());
        userOutputDto.setPassword(u.getPassword());
        return userOutputDto;
    }

    public User makeUser (UserInputDto userInputDto) {
        User user = new User();
        user.setEmail(userInputDto.getEmail());
        user.setRole(roleRepository.findByRoleName(userInputDto.getRoleName()));
        user.setPassword(userInputDto.getPassword());
        // TO-DO: moet nog toegevoegd worden: API-key en enabled?
        return user;

    }

    public void updateUser(String email, UserInputDto newUserInfo) {
        if (userRepository.findByEmail(email) == null) throw new RecordNotFoundException();
        User user = userRepository.findByEmail(email);
        user.setPassword(newUserInfo.getPassword());
        userRepository.save(user);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.deleteById(user.getId());
    }

    public String getAuthorities(String email) {
        if (userRepository.findByEmail(email) == null) throw new org.springframework.security.core.userdetails.UsernameNotFoundException(email);
        User user = userRepository.findByEmail(email);
        UserOutputDto userDto = makeTheDto(user);
        return userDto.getRoleName();
    }

    public String createUser(UserInputDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(makeUser(userDto));
        return newUser.getEmail();
    }

    public void addRole(String email, String roleName) {

        if (userRepository.findByEmail(email) == null) throw new org.springframework.security.core.userdetails.UsernameNotFoundException(email);
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByRoleName(roleName);
        user.setRole(role);
        userRepository.save(user);
    }
}
