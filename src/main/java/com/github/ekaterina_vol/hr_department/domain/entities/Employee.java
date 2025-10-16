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
public class Employee {
    public enum Sex {
        MALE,
        FEMALE
    }

    private Long employeeId;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private Sex sex;
    private LocalDateTime createdAt;
}
