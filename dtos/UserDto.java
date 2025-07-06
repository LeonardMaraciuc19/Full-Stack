package com.its.library.dtos;

import com.its.library.entities.User;

public class UserDto {
    public int Id;

    public String Name;
    public String LastName;

    public UserDto(User user) {
        this.Id = user.Id;
        this.Name = user.Name;
        this.LastName = user.LastName;
    }
}