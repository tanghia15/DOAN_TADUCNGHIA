package com.example.nghiahaui.service;

import com.example.nghiahaui.entity.User;
import com.example.nghiahaui.model.request.ChangePasswordRequest;
import com.example.nghiahaui.model.request.CreateUserRequest;
import com.example.nghiahaui.model.request.UpdateProfileRequest;

import java.util.List;

public interface UserService {
    
    void register(CreateUserRequest request);

    List<User> findAll();
    User getUserByUsername(String username);

    User updateUser(UpdateProfileRequest request);

    void changePassword(ChangePasswordRequest request);

    void enableUser(long id);
}
