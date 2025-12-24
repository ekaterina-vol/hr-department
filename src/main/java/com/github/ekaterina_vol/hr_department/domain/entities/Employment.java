package com.github.ekaterina_vol.hr_department.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employment {
    private Long employmentId;
    private Long employeeId;
    private Long postId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
