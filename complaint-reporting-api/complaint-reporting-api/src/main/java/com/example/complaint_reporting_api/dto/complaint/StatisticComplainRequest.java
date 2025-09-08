package com.example.complaint_reporting_api.dto.complaint;

import com.example.complaint_reporting_api.entity.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticComplainRequest {
    private Status status;
    private long count;

}
