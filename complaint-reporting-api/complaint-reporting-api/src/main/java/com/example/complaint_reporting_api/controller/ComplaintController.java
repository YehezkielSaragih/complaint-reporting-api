package com.example.complaint_reporting_api.controller;

import com.example.complaint_reporting_api.dto.complaint.ChangeComplainStatusRequest;
import com.example.complaint_reporting_api.dto.complaint.CreateComplainRequest;
import com.example.complaint_reporting_api.entity.ComplaintEntity;
import com.example.complaint_reporting_api.service.ComplaintService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    // create complaint
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplaintEntity> createComplaint(@Valid @RequestBody CreateComplainRequest req){
        return complaintService.createComplaints(req);
    }

    // get all complaint
    @GetMapping
    public ResponseEntity<List<ComplaintEntity>> getAllComplaint(@RequestParam(required = false) String status){
        return complaintService.getAllComplaint(status);
    }

    // get complaint detail
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintEntity> getComplaintDetail(@PathVariable Long id){
        return complaintService.getComplaintDetail(id);

    }

    // update complaint status
    @PutMapping("/{id}")
    public ResponseEntity<ComplaintEntity> updateComplaintStatus(@PathVariable Long id, @RequestBody ChangeComplainStatusRequest status) {
        return complaintService.updateStatus(id, status);
    }

}
