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
public class UserEntity {

    private Long userId;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Email harus valid")
    private String email;

    private List<ComplaintEntity> complaints;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}

