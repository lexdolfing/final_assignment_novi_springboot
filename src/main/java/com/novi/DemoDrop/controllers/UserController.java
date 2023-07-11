package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.UserInputDto;
import com.novi.DemoDrop.Dto.OutputDto.UserOutputDto;
import com.novi.DemoDrop.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserOutputDto>> getUsers() {

        List<UserOutputDto> userOutputDtos = userService.getAllUsers();

        return ResponseEntity.ok().body(userOutputDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String email, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInUsername = userDetails.getUsername();
        if (loggedInUsername.equals(email)) {
            UserOutputDto userOutputDto = userService.getUserByEmail(email);
            return ResponseEntity.ok().body(userOutputDto);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Return a forbidden response if the usernames don't match
        }
    }

    @GetMapping(value = "/{email}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.getAuthorities(email));
    }

    @PostMapping(value = "")
    public ResponseEntity<UserInputDto> createUser(@RequestBody UserInputDto dto) {

        String newUsername = userService.createUser(dto);
        if (dto.getEmail().contains("@elevaterecords.nl")){
            userService.addRole(newUsername, "ROLE_ADMIN");
        } else {
            userService.addRole(newUsername, "ROLE_USER");
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteDJ(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }



}