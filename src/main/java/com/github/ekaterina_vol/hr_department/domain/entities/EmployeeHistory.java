package com.github.ekaterina_vol.hr_department.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHistory {
    private Long history_id;
    private LocalDateTime change_data;
    private Long employee_id;
    private String last_name;
}
