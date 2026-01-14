package com.signallist.controller;

import com.signallist.dto.UserDTO;
import com.signallist.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    public final UserService service;


    public UserController(UserService service) {
      this.service = service;
    }

    @GetMapping("/withEmail")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = service.getAllUsersWithEmail();
        return ResponseEntity.ok(users);
    }
}

