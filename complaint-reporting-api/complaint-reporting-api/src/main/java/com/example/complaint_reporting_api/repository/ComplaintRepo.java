package com.example.complaint_reporting_api.repository;

import com.example.complaint_reporting_api.entity.ComplaintEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ComplaintRepo {

    private final Map<Long, ComplaintEntity> data = new ConcurrentHashMap<Long, ComplaintEntity>();
    private final AtomicLong seq = new AtomicLong(0);

    public List<ComplaintEntity> findAll() { return new ArrayList<ComplaintEntity>(data.values()); }
    public Optional<ComplaintEntity> findById(Long id) { return Optional.ofNullable(data.get(id)); }
    public ComplaintEntity save(ComplaintEntity c) {
        if (c.getComplaintId() == null) c.setComplaintId(seq.incrementAndGet());
        data.put(c.getComplaintId(), c);
        return c;
    }

    public void delete(Long id) { data.remove(id); }
    public boolean exists(Long id) { return data.containsKey(id); }
}
