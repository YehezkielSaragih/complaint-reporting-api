package com.example.complaint_reporting_api.service;

import com.example.complaint_reporting_api.dto.user.RegisterUsersRequest;
import com.example.complaint_reporting_api.entity.ComplaintEntity;
import com.example.complaint_reporting_api.entity.UserEntity;
import com.example.complaint_reporting_api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<UserEntity> registerUser(RegisterUsersRequest request) {
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .build();
        return ResponseEntity.ok(userRepo.save(user));
    }

    public List<ComplaintEntity> getUserComplaints(Long userId){
        Optional<UserEntity> userOpt = userRepo.findById(userId);
        return userOpt.map(UserEntity::getComplaints).orElse(List.of());
    }
}
