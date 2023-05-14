package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.entity.User;
import com.rachev.ethereumfetcher.model.user.UserDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    void saveUser(UserDto user);

    @Transactional(propagation = Propagation.NESTED)
    void updateUser(User user);

    User getUserByUsername(String username);

    User getUserById(Long id);
}
