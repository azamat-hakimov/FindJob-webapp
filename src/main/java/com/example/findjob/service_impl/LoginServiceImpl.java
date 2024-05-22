package com.example.findjob.service_impl;

import org.springframework.stereotype.Service;

import com.example.findjob.entity.User;
import com.example.findjob.repository.LoginRepository;
import com.example.findjob.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

    private LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void saveUser(User user) {
        loginRepository.save(user);
    }

    @Override
    public boolean usernameExists(String username) {
        return loginRepository.existsByUsername(username);
    }

    @Override
    public User getUserByUsername(String username) {
        return loginRepository.findByUsername(username);
    }
    
}
