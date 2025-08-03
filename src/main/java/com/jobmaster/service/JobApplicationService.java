package com.jobmaster.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobmaster.dto.JobApplicationDTO;
import com.jobmaster.model.Company;
import com.jobmaster.model.JobApplication;
import com.jobmaster.model.JobStatus;
import com.jobmaster.model.User;
import com.jobmaster.repository.JobApplicationRepository;
import com.jobmaster.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final CompanyService companyService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public JobApplicationDTO createJob(JobApplicationDTO dto) {
    	Company company = companyService.getOrCreateCompanyByName(dto.getCompanyName());
        User user = getCurrentUser();
        
        JobApplication job = JobApplication.builder()
                .position(dto.getPosition())
                .location(dto.getLocation())
                .status(JobStatus.valueOf(dto.getStatus()))
                .appliedDate(dto.getAppliedDate())
                .followUpDate(dto.getFollowUpDate())
                .jobLink(dto.getJobLink())
                .notes(dto.getNotes())
                .user(user)
                .company(company)
                .build();
     
        	job = jobApplicationRepository.save(job);
        	return mapToDTO(job);
    }

    public List<JobApplicationDTO> getAllJobsForCurrentUser() {
        User user = getCurrentUser();
        return user.getJobApplications().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    public JobApplicationDTO getJobById(Long id) {
        JobApplication jobApplication = getJobByIdForCurrentUser(id);
        return mapToDTO(jobApplication);
    }
     

    public JobApplicationDTO updateJob(Long id, JobApplicationDTO dto) {
    	Company company = companyService.getOrCreateCompanyByName(dto.getCompanyName());
        JobApplication existingJob = getJobByIdForCurrentUser(id);
        existingJob.setCompany(company);
        existingJob.setPosition(dto.getPosition());
        existingJob.setLocation(dto.getLocation());
        existingJob.setStatus(JobStatus.valueOf(dto.getStatus()));
        existingJob.setAppliedDate(dto.getAppliedDate());
        existingJob.setFollowUpDate(dto.getFollowUpDate());
        existingJob.setJobLink(dto.getJobLink());
        existingJob.setNotes(dto.getNotes());
        existingJob = jobApplicationRepository.save(existingJob);
    	return mapToDTO(existingJob);
    }

    public void deleteJob(Long id) {
        JobApplication existingJob = getJobByIdForCurrentUser(id);
        jobApplicationRepository.delete(existingJob);
    }
    
    private JobApplicationDTO mapToDTO(JobApplication jobApp) {
        JobApplicationDTO dto = new JobApplicationDTO();
        dto.setId(jobApp.getId());
        dto.setCompanyName(jobApp.getCompany().getName());
        dto.setPosition(jobApp.getPosition());
        dto.setLocation(jobApp.getLocation());
        dto.setStatus(jobApp.getStatus().name());
        dto.setAppliedDate(jobApp.getAppliedDate());
        dto.setFollowUpDate(jobApp.getFollowUpDate());
        dto.setJobLink(jobApp.getJobLink());
        dto.setNotes(jobApp.getNotes());
        dto.setUserId(jobApp.getUser().getId());
        return dto;
    }
    
    private JobApplication getJobByIdForCurrentUser(Long id) {
        User user = getCurrentUser();
        return jobApplicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Job not found or unauthorized"));
    }

}
