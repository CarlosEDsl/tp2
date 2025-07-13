package com.TP.account_service.controllers;

import com.TP.account_service.models.DTOs.PublicProfileResponseDTO;
import com.TP.account_service.models.DTOs.UserRequestDTO;
import com.TP.account_service.models.User;
import com.TP.account_service.services.UserService;
import com.TP.account_service.models.DTOs.UserResponseDTO;
import com.TP.account_service.models.DTOs.UserUpdateReqDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID id) {
        User user = this.userService.findUser(id);
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getBio()
        );

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/getEmail/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        User user = this.userService.findUserByEmail(email);
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getBio()
        );
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequestDTO user) {
        this.userService.createUser(user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UserUpdateReqDTO user) {
        User userUpdated = this.userService.updateUser(id, user);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/publicProfile/{id}")
    public ResponseEntity<PublicProfileResponseDTO> getPublicProfileById(@PathVariable UUID id) {
        PublicProfileResponseDTO publicProfile = this.userService.getPublicProfileById(id);
        return ResponseEntity.ok(publicProfile);
    }

}
