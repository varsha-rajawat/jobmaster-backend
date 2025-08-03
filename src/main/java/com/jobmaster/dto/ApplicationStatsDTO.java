package com.jobmaster.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatsDTO {
    private List<ApplicationStatusCountDTO> statusCounts;
    private List<WeeklyApplicationCountDTO> weeklyCounts;
    private List<MonthlyApplicationCountDTO> monthlyCounts;
    private double offerToApplicationRatio;
}

