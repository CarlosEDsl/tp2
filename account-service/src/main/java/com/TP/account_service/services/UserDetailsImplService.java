package com.TP.account_service.services;

import com.TP.account_service.models.User;
import com.TP.account_service.repositories.UserRepository;
import com.TP.account_service.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsImplService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImplService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findUserByEmail(email);
        if(user.isEmpty()){
            throw new RuntimeException("Email not found for login");
        }
        return new UserDetailsImpl(user.get());
    }
}
