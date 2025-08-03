package com.jobmaster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobmaster.dto.JobApplicationDTO;
import com.jobmaster.service.JobApplicationService;

@RestController
@RequestMapping("/jobs")
public class JobApplicationController {

	private final JobApplicationService jobApplicationService;

	public JobApplicationController(JobApplicationService jobApplicationService) {
		this.jobApplicationService = jobApplicationService;
	}

	@PostMapping
	public ResponseEntity<JobApplicationDTO> createJob(@RequestBody JobApplicationDTO dto) {
		return ResponseEntity.ok(jobApplicationService.createJob(dto));
	}

	@GetMapping
	public ResponseEntity<List<JobApplicationDTO>> getAllJobs() {
		return ResponseEntity.ok(jobApplicationService.getAllJobsForCurrentUser());
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobApplicationDTO> getJobById(@PathVariable Long id) {
		return ResponseEntity.ok(jobApplicationService.getJobById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<JobApplicationDTO> updateJob(@PathVariable Long id, @RequestBody JobApplicationDTO dto) {
		return ResponseEntity.ok(jobApplicationService.updateJob(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
		jobApplicationService.deleteJob(id);
		return ResponseEntity.noContent().build();
	}
}
