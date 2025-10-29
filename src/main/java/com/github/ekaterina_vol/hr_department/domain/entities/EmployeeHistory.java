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
public class EmployeeHistory {
    private Long historyId;
    private LocalDateTime changeDate;
    private Long employeeId;
    private String lastName;
}
