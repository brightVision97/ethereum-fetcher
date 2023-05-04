package com.rachev.ethereumfetcher.service.base;

import com.rachev.ethereumfetcher.entity.User;
import com.rachev.ethereumfetcher.model.user.UserDto;

public interface UserService {

    void saveUser(UserDto user);

    void updateUser(User user);

    User getUserByUsername(String username);
}
