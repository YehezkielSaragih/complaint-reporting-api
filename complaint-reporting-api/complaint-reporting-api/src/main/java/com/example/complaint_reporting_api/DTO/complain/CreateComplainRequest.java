package com.example.complaint_reporting_api.DTO.complain;

import com.example.complaint_reporting_api.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateComplainRequest {

    private Long userId;

    @NotBlank(message = "Deskripsi keluhan tidak boleh kosong")
    @Size(min = 20, message = "Deskripsi minimal 20 karakter")
    private String description;

    private Status status;
}
