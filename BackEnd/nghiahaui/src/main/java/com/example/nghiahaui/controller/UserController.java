package com.example.nghiahaui.controller;

import com.example.nghiahaui.model.request.UpdateStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import com.example.nghiahaui.entity.User;
import com.example.nghiahaui.model.request.ChangePasswordRequest;
import com.example.nghiahaui.model.request.UpdateProfileRequest;
import com.example.nghiahaui.model.response.MessageResponse;
import com.example.nghiahaui.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;
    

    @GetMapping("/listuser")
    @Operation(summary="Lấy ra danh sach username")
    public ResponseEntity<?> getlistuser(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/")
    @Operation(summary="Lấy ra user bằng username")
    public ResponseEntity<User> getuser(@RequestParam("username") String username){
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    @Operation(summary="Cập nhật user")
    public ResponseEntity<User> updateProfile(@RequestBody UpdateProfileRequest request){
        User user = userService.updateUser(request);

        return ResponseEntity.ok(user);
    }

     @PutMapping("/password")
     public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
         userService.changePassword(request);
         return ResponseEntity.ok(new MessageResponse("Change Password Success!"));
     }

    @PutMapping("/enable/{id}")
    @Operation(summary="Kích hoạt user bằng id")
    public ResponseEntity<?> enabled(@PathVariable long id){
        userService.enableUser(id);
        return ResponseEntity.ok(new MessageResponse("Enable tag success"));

    }

}
