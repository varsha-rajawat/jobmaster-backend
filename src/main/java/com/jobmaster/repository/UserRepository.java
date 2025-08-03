package com.jobmaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobmaster.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
