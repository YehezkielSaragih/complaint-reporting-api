package com.example.complaint_reporting_api.dto.complaint;

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
