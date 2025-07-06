package com.its.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.its.library.dtos.UserDto;
import com.its.library.entities.User;
import com.its.library.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userrepository;

    public UserDto GetUserByName(String name, String lastName) {
        User user = userrepository.SeachByName(name, lastName);
        if (user == null)
            return null;
        return new UserDto(user);
    }

    @Transactional
    public User AddUser(User user) {
        return userrepository.save(user);
    }
}
