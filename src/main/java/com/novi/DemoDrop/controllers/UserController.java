package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.UserInputDto;
import com.novi.DemoDrop.Dto.OutputDto.UserOutputDto;
import com.novi.DemoDrop.services.UserService;
import org.springframework.http.ResponseEntity;
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

    //TO-DO: add security that a user can only see his own info
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String email) {

        UserOutputDto userOutputDto = userService.getUserByEmail(email);

        return ResponseEntity.ok().body(userOutputDto);

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

    //TO-DO: add security that a user can only see his own info
    @PutMapping(value = "/{email}")
    public ResponseEntity<UserOutputDto> updateDJ(@PathVariable("email") String username, @RequestBody UserInputDto dto) {

        userService.updateUser(username, dto);

        return ResponseEntity.noContent().build();
    }
    //TO-DO: add security that a user can only see his own info
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteDJ(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{email}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.getAuthorities(email));
    }

// TO-DO Ga ik onderstaande niet gebruiken?
//    @PostMapping(value = "/{username}/authorities")
//    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
//        try {
//            String authorityName = (String) fields.get("authority");
//            userService.addAuthority(username, authorityName);
//            return ResponseEntity.noContent().build();
//        }
//        catch (Exception ex) {
//            throw new BadRequestException();
//        }
//
//   }

    // Ga ik ook niet gebruiken?
//    @DeleteMapping(value = "/{username}/authorities/{authority}")
//    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
//        userService.removeAuthority(username, authority);
//        return ResponseEntity.noContent().build();
//    }

}