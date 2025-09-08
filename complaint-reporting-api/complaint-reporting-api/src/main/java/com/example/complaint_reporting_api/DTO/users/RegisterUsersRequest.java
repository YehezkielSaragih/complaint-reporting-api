package com.example.complaint_reporting_api.DTO.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUsersRequest {
    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Email harus valid")
    private String email;
}
