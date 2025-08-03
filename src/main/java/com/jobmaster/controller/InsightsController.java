package com.jobmaster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobmaster.dto.ApplicationStatsDTO;
import com.jobmaster.service.InsightsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/insights")
@RequiredArgsConstructor
public class InsightsController {
	
	private final InsightsService insightsService;
	
	@GetMapping("/all")
	public ResponseEntity<ApplicationStatsDTO> getAllInsights(@RequestParam Long userId) {
	    return ResponseEntity.ok(insightsService.getApplicationStats(userId));
	}


}
