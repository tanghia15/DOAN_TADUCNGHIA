package com.example.nghiahaui.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.nghiahaui.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nghiahaui.exception.BadRequestException;
import com.example.nghiahaui.exception.NotFoundException;
import com.example.nghiahaui.model.request.ChangePasswordRequest;
import com.example.nghiahaui.model.request.CreateUserRequest;
import com.example.nghiahaui.model.request.UpdateProfileRequest;
import com.example.nghiahaui.repository.RoleRepository;
import com.example.nghiahaui.repository.UserRepository;
import com.example.nghiahaui.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void register(CreateUserRequest request) {
        boolean usernameExists = userRepository.existsByUsername(request.getUsername());
        if (usernameExists) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        // TODO Auto-generated method stub
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setEnabled(true);
        Set<String> strRoles = request.getRole();
          Set<Role> roles = new HashSet<>();
      
          if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          } else {
            strRoles.forEach(role -> {
              switch (role) {
              case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
      
                break;
              case "mod":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
      
                break;
              default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
              }
            });
          }
          user.setRoles(roles);
          userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User"));
      return user;
    }

    @Override
    public User updateUser(UpdateProfileRequest request) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User"));
      user.setFirstname(request.getFirstname());
      user.setLastname(request.getLastname());
      user.setEmail(request.getEmail());
      user.setCountry(request.getCountry());
      user.setState(request.getState());
      user.setAddress(request.getAddress());
      user.setPhone(request.getPhone());
      userRepository.save(user);
      return user;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
      // TODO Auto-generated method stub
       User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User"));

        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old Password Not Same");
        }
       user.setPassword(encoder.encode(request.getNewPassword()));

       userRepository.save(user);
      
    }
    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        List<User> list = userRepository.findAll(Sort.by("id").descending());
        return list;
    }
    @Override
    public void enableUser(long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Tag With Id: " + id));
        if(user.isEnabled()){
            user.setEnabled(false);
        } else{
            user.setEnabled(true);
        }
        userRepository.save(user);
    }



}
