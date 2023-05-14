package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.User;
import com.rachev.ethereumfetcher.model.user.UserDto;
import com.rachev.ethereumfetcher.repository.UserRepository;
import com.rachev.ethereumfetcher.service.base.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder bcryptEncoder;

    @Override
    public void saveUser(UserDto user) {
        var toSave = User.builder()
                .username(user.getUsername())
                .password(bcryptEncoder.encode(user.getPassword()))
                .build();
        userRepository.save(toSave);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));
    }
}
