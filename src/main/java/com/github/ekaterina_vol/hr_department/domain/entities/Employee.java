package com.github.ekaterina_vol.hr_department.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    public enum Sex {
        MALE,
        FEMALE
    }

    private Long employee_id;
    private String first_name;
    private String last_name;
    private LocalDateTime birth_date;
    private Sex sex;
    private LocalDateTime created_at;
}
