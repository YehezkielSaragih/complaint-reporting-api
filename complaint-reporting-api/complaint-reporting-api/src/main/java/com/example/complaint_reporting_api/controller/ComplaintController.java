package com.example.complaint_reporting_api.controller;

import com.example.complaint_reporting_api.dto.complaint.CreateComplainRequest;
import com.example.complaint_reporting_api.entity.ComplaintEntity;
import com.example.complaint_reporting_api.entity.Status;
import com.example.complaint_reporting_api.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplaintEntity> createComplaint(@Valid @RequestBody CreateComplainRequest req){
        return complaintService.createComplaints(req);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintEntity> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam Status status
    ) {
        return complaintService.updateStatus(id, status);
    }

}
