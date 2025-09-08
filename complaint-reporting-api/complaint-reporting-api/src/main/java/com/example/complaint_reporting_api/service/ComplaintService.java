package com.example.complaint_reporting_api.service;

import com.example.complaint_reporting_api.dto.complaint.ChangeComplainStatusRequest;
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

    public ResponseEntity<ComplaintEntity> createComplaints(CreateComplainRequest req){
        try{
            Status stats = Status.valueOf(req.getStatus().toUpperCase());
            ComplaintEntity tempComplaint = ComplaintEntity.builder()
                    .description(req.getDescription())
                    .status(stats)
                    .userId(req.getUserId())
                    .build();
            return ResponseEntity.ok(complaintRepo.save(tempComplaint));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

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
}
