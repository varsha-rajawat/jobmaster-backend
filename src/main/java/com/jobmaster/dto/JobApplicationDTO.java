package com.jobmaster.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class JobApplicationDTO {
    private Long id;
    private String companyName;
    private String position;
    private String location;
    private String status;
    private LocalDate appliedDate;
    private LocalDate followUpDate;
    private String jobLink;
    private String notes;
    private Long userId; // only reference the ID
}
