package com.example.findjob.service;

import com.example.findjob.entity.User;

public interface LoginService {
    void saveUser(User user);
    boolean usernameExists(String username);
    User getUserByUsername(String username);
}
