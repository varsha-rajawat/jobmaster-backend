package com.jobmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyApplicationCountDTO {
    private String month; // e.g., "2025-05"
    private long count;
}
