package com.example.complaint_reporting_api.service;

import com.example.complaint_reporting_api.dto.complaint.CreateComplainRequest;
import com.example.complaint_reporting_api.entity.ComplaintEntity;
import com.example.complaint_reporting_api.entity.Status;
import com.example.complaint_reporting_api.repository.ComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepo complaintRepo;

    public ResponseEntity<ComplaintEntity> createComplaints(CreateComplainRequest req){
        ComplaintEntity tempComplaint = ComplaintEntity.builder()
                .description(req.getDescription())
                .status(Status.valueOf(req.getStatus().toUpperCase()))
                .userId(req.getUserId())
                .build();
        return ResponseEntity.ok(complaintRepo.save(tempComplaint));
    }

    public ResponseEntity<ComplaintEntity> updateStatus(Long id, Status status) {
        Optional<ComplaintEntity> complaintOpt = complaintRepo.findById(id);
        if (complaintOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ComplaintEntity tempComplaint = complaintOpt.get();
        tempComplaint.setStatus(status);
        tempComplaint.setUpdatedAt(LocalDateTime.now());
        complaintRepo.save(tempComplaint);
        return ResponseEntity.ok(tempComplaint);
    }
}
