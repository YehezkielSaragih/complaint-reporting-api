package com.example.complaint_reporting_api.entity;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintEntity {

    private Long complaintId;
    private Long userId;

    @NotBlank(message = "Deskripsi keluhan tidak boleh kosong")
    private String description;

    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
