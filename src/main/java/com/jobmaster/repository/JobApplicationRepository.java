package com.jobmaster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobmaster.model.JobApplication;
import com.jobmaster.model.User;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
    List<JobApplication> findByUser(User user);
    Optional<JobApplication> findByIdAndUser(Long id, User user);
}
