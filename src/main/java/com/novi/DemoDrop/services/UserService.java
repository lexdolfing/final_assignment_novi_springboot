package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.UserOutputDto;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public createUser(UserInputDto userInputDto)

    public UserOutputDto makeTheDto (User u) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(u.getId());
        userOutputDto.setEmail(u.getEmail());
        userOutputDto.setPassword(u.getPassword());
        if (u.getTalentManager() != null) {
            userOutputDto.setTalentManagerId(u.getTalentManager().getId());
        }
        if (u.getDj() != null) {
            userOutputDto.setDjId(u.getDj().getId());
        }
        return userOutputDto;
    }
}
