package com.example.complaint_reporting_api.repository;

import com.example.complaint_reporting_api.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepo {

    private final Map<Long, UserEntity> users = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    public List<UserEntity> findAll() { return new ArrayList<UserEntity>(users.values()); }
    public Optional<UserEntity> findById(Long id) { return Optional.ofNullable(users.get(id)); }

    public UserEntity save(UserEntity user) {
        if (user.getUserId() == null) {
            user.setUserId(seq.incrementAndGet());
            user.setCreatedAt(java.time.LocalDateTime.now());
        }
        else{
            users.put(user.getUserId(), user);
            user.setUpdatedAt(java.time.LocalDateTime.now());
        }
        return user;
    }

    public void delete(Long id) {
        UserEntity user = users.get(id);
        if (user != null) {
            user.setDeletedAt(java.time.LocalDateTime.now());
        }
    }
}
