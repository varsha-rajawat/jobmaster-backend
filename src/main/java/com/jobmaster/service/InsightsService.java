package com.jobmaster.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobmaster.dto.ApplicationStatsDTO;
import com.jobmaster.dto.ApplicationStatusCountDTO;
import com.jobmaster.dto.MonthlyApplicationCountDTO;
import com.jobmaster.dto.WeeklyApplicationCountDTO;
import com.jobmaster.model.JobApplication;
import com.jobmaster.model.JobStatus;
import com.jobmaster.repository.JobApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsightsService {
	
	private final JobApplicationRepository jobApplicationRepository;
	
	public ApplicationStatsDTO getApplicationStats(Long userId) {
	    List<JobApplication> applications = jobApplicationRepository.findByUserId(userId);

	    // Status count
	    List<ApplicationStatusCountDTO> statusCounts = applications.stream()
	            .collect(Collectors.groupingBy(app -> app.getStatus().name(), Collectors.counting()))
	            .entrySet().stream()
	            .map(entry -> new ApplicationStatusCountDTO(entry.getKey(), entry.getValue()))
	            .collect(Collectors.toList());

	    // Weekly count
	    LocalDate oneWeekAgo = LocalDate.now().minusDays(6);
	    List<WeeklyApplicationCountDTO> weeklyCounts = applications.stream()
	    		.filter(app -> app.getAppliedDate() != null)
	            .filter(app -> !app.getAppliedDate().isBefore(oneWeekAgo))
	            .collect(Collectors.groupingBy(JobApplication::getAppliedDate, Collectors.counting()))
	            .entrySet().stream()
	            .map(entry -> new WeeklyApplicationCountDTO(entry.getKey(), entry.getValue()))
	            .collect(Collectors.toList());

	    // Monthly count (group by month name or number)
	    List<MonthlyApplicationCountDTO> monthlyCounts = applications.stream()
	    		.filter(app -> app.getAppliedDate() != null)
	            .collect(Collectors.groupingBy(
	                    app -> app.getAppliedDate().getMonth().toString(), // or app.getAppliedDate().getMonthValue()
	                    Collectors.counting()))
	            .entrySet().stream()
	            .map(entry -> new MonthlyApplicationCountDTO(entry.getKey(), entry.getValue()))
	            .collect(Collectors.toList());

	    // Offer to application ratio
	    long total = applications.size();
	    long offers = applications.stream()
	            .filter(app -> app.getStatus() == JobStatus.OFFER)
	            .count();
	    double ratio = total == 0 ? 0.0 : (double) offers / total;

	    return new ApplicationStatsDTO(statusCounts, weeklyCounts, monthlyCounts, ratio);
	}


}
