package com.example.complaint_reporting_api.repository;

import com.example.complaint_reporting_api.entity.ComplaintEntity;
import com.example.complaint_reporting_api.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ComplaintRepo {

    private final Map<Long, ComplaintEntity> complaints = new ConcurrentHashMap<Long, ComplaintEntity>();
    private final AtomicLong seq = new AtomicLong(0);

    public List<ComplaintEntity> findAll() { return new ArrayList<ComplaintEntity>(complaints.values()); }
    public Optional<ComplaintEntity> findById(Long id) { return Optional.ofNullable(complaints.get(id)); }

    public ComplaintEntity save(ComplaintEntity complaint) {
        if (complaint.getComplaintId() == null) {
            complaint.setComplaintId(seq.incrementAndGet());
            complaint.setCreatedAt(java.time.LocalDateTime.now());
        }
        complaints.put(complaint.getComplaintId(), complaint);
        complaint.setUpdatedAt(java.time.LocalDateTime.now());
        return complaint;
    }

    public void delete(Long id) {
        ComplaintEntity complaint = complaints.get(id);
        if (complaint != null) {
            complaint.setDeletedAt(java.time.LocalDateTime.now());
        }
    }
}
