package com.github.ekaterina_vol.hr_department.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employment {
    private Long employment_id;
    private Long employee_id;
    private Long post_id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;

}
