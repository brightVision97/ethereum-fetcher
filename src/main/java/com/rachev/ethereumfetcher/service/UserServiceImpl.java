package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.User;
import com.rachev.ethereumfetcher.model.user.UserDto;
import com.rachev.ethereumfetcher.repository.UserRepository;
import com.rachev.ethereumfetcher.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder bcryptEncoder;

    @Override
    public void saveUser(UserDto user) {
        User toSave = User.builder()
                .username(user.getUsername())
                .password(bcryptEncoder.encode(user.getPassword()))
                .build();
        userRepository.save(toSave);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

}
