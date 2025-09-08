package com.example.complaint_reporting_api.DTO.complain;

import com.example.complaint_reporting_api.entity.Status;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeComplainStatusRequest {

    private Status status;
}
