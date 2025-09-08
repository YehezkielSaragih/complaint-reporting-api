package com.example.complaint_reporting_api.service;

import com.example.complaint_reporting_api.dto.complaint.ChangeComplainStatusRequest;
import com.example.complaint_reporting_api.dto.complaint.CreateComplainRequest;
import com.example.complaint_reporting_api.dto.complaint.StatisticComplainRequest;
import com.example.complaint_reporting_api.entity.ComplaintEntity;
import com.example.complaint_reporting_api.entity.Status;
import com.example.complaint_reporting_api.entity.UserEntity;
import com.example.complaint_reporting_api.repository.ComplaintRepo;

import com.example.complaint_reporting_api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepo complaintRepo;
    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<ComplaintEntity> createComplaints(CreateComplainRequest req){
        // Check user id
        Optional<UserEntity> userOpt = userRepo.findById(req.getUserId());
        if (userOpt.isEmpty() || userOpt.get().getDeletedAt() != null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            // Create
            Status stats = Status.valueOf(req.getStatus().toUpperCase());
            ComplaintEntity tempComplaint = ComplaintEntity.builder()
                    .description(req.getDescription())
                    .status(stats)
                    .userId(req.getUserId())
                    .createdAt(LocalDateTime.now())
                    .build();
            // Add to complaint list
            ComplaintEntity saved = complaintRepo.save(tempComplaint);
            // Add to user complaint list
            UserEntity user = userOpt.get();
            if (user.getComplaints() == null) {
                user.setComplaints(new ArrayList<>());
            }
            user.getComplaints().add(saved);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<ComplaintEntity>> getAllComplaint(String status) {
        List<ComplaintEntity> listData = complaintRepo.findAll().stream()
                .filter(c -> c.getDeletedAt() == null)
                .collect(Collectors.toList());
        if (status == null || status.isBlank()) {
            return ResponseEntity.ok(listData);
        }
        try {
            Status stats = Status.valueOf(status.toUpperCase());
            List<ComplaintEntity> filteredData = listData.stream()
                    .filter(c -> c.getStatus() == stats)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<ComplaintEntity> getComplaintDetail(Long id){
        ComplaintEntity tempComplaint = complaintRepo.findById(id).orElse(null);
        if (tempComplaint == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tempComplaint);
    }

    public ResponseEntity<ComplaintEntity> updateStatus(Long id, ChangeComplainStatusRequest status) {
        Optional<ComplaintEntity> complaintOpt = complaintRepo.findById(id);
        if (complaintOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try{
            ComplaintEntity tempComplaint = complaintOpt.get();
            Status stats = Status.valueOf(status.getStatus().toUpperCase());
            tempComplaint.setStatus(stats);
            tempComplaint.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(complaintRepo.save(tempComplaint));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<ComplaintEntity> deleteComplaint(Long id) {
        Optional<ComplaintEntity> complaintOpt = complaintRepo.findById(id);
        if (complaintOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        complaintRepo.delete(id);
        return ResponseEntity.ok(complaintOpt.get());
    }

    public ResponseEntity<List<StatisticComplainRequest>> getStatistics() {
        List<ComplaintEntity> activeComplaints = complaintRepo.findAll().stream()
                .filter(c -> c.getDeletedAt() == null)
                .toList();
        List<StatisticComplainRequest> stats = Arrays.stream(Status.values())
                .map(status -> new StatisticComplainRequest(
                        status,
                        activeComplaints.stream()
                                .filter(c -> c.getStatus() == status)
                                .count()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stats);
    }
}
