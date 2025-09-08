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

import java.util.ArrayList;
import java.util.List;


@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepo complaintRepo;

    public ResponseEntity<List<ComplaintEntity>> getAllComplaint(String status){
        List<ComplaintEntity> listData = complaintRepo.findAll();
        List<ComplaintEntity> filteredData = new ArrayList<>();
        if(status.isEmpty()) return ResponseEntity.ok(listData);

        try{
            Status stats = Status.valueOf(status.toUpperCase());
            for(ComplaintEntity c : listData){
                if (c.getStatus().equals(stats)) filteredData.add(c);
            }

            return ResponseEntity.ok(filteredData);
        } catch (IllegalArgumentException e){
            return ResponseEntity.noContent().build();
        }

    }

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

    public ResponseEntity<ComplaintEntity> getComplaintDetail(Long id){
        ComplaintEntity tempComplaint = complaintRepo.findById(id).orElse(null);
        if (tempComplaint == null) return ResponseEntity.notFound().build();


        return ResponseEntity.ok(tempComplaint);
    }
}
