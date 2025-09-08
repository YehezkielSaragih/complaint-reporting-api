package com.example.complaint_reporting_api.controller;

import com.example.complaint_reporting_api.entity.UserEntity;
import com.example.complaint_reporting_api.entity.ComplaintEntity;
import com.example.complaint_reporting_api.dto.user.RegisterUsersRequest;
import com.example.complaint_reporting_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> registerUser(@Valid @RequestBody RegisterUsersRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping("/{userId}/complaints")
    public List<ComplaintEntity> getUserComplaints(@PathVariable Long userId) {
        return userService.getUserComplaints(userId);
    }
}
