package com.TP.account_service.factory;

import com.TP.account_service.models.DTOs.UserRequestDTO;
import com.TP.account_service.mappers.UserMapper;
import com.TP.account_service.models.DTOs.UserUpdateReqDTO;
import com.TP.account_service.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserFactory(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User fromRequest(UserRequestDTO dto) {
        validMailVerification(dto.email());

        return new User(
                dto.username(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );
    }

    public User fromRequestUpdate(User oldUser, UserUpdateReqDTO updatedUser) {
        if(!(oldUser.getEmail().equals(updatedUser.email())) && updatedUser.email() != null) {
            validMailVerification(updatedUser.email());
        }

        userMapper.updateUserFromDto(updatedUser, oldUser);

        return oldUser;
    }

    private void validMailVerification(String email){
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("O email fornecido é inválido.");
        }
    }
}

