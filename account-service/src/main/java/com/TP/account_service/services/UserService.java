package com.TP.account_service.services;

import com.TP.account_service.factory.UserFactory;
import com.TP.account_service.models.DTOs.UserRequestDTO;
import com.TP.account_service.repositories.UserRepository;
import com.TP.account_service.models.DTOs.UserUpdateReqDTO;
import com.TP.account_service.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public UserService(UserRepository userRepository, UserFactory userFactory) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void createUser(UserRequestDTO userDTO) {
        User newUser = userFactory.fromRequest(userDTO);

        this.uniqueEmailVerification(userDTO.email());

        userRepository.save(newUser);
    }

    public User findUser(UUID id) {
        Optional<User> userFound = this.userRepository.findById(id);
        if(userFound.isEmpty()){
            throw new RuntimeException("Usuário não encontrado no ID específicado");
        }

        return userFound.get();
    }

    public User findUserByEmail(String email){
        Optional<User> userFound = this.userRepository.findUserByEmail(email);
        if(userFound.isEmpty()){
            throw new RuntimeException("Email não encontrado entre os cadastros");
        }

        return userFound.get();
    }

    @Transactional
    public User updateUser(UUID id, UserUpdateReqDTO userUpdateReqDTO) {
        User currentUser = this.findUser(id);
        User updatedUser = this.userFactory.fromRequestUpdate(currentUser, userUpdateReqDTO);

        if(!Objects.equals(updatedUser.getEmail(), userUpdateReqDTO.email()) && userUpdateReqDTO.email() != null)
            this.uniqueEmailVerification(updatedUser.getEmail());

        this.userRepository.save(updatedUser);
        return updatedUser;
    }

    public void deleteUser(UUID id) {
        User user = this.findUser(id);
        this.userRepository.delete(user);
    }

    private void uniqueEmailVerification(String email){
        Optional<User> userSearch = this.userRepository.findUserByEmail(email);
        if(userSearch.isPresent()){
            throw new RuntimeException("Este email já está cadastrado");
        }
    }

}
