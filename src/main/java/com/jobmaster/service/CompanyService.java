package com.jobmaster.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobmaster.dto.CompanyDTO;
import com.jobmaster.model.Company;
import com.jobmaster.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;

	public Company getOrCreateCompanyByName(String name) {
		 return companyRepository.findByNameIgnoreCase(name.trim()).orElseGet(() -> {
			Company newCompany = new Company();
			newCompany.setName(name.trim());
			return companyRepository.save(newCompany);
		});
	}

	public Optional<CompanyDTO> findById(Long id) {
		return companyRepository.findById(id)
		    .map(company -> new CompanyDTO(company.getId(), company.getName()));
	}


	public CompanyDTO save(Company company) {
		company = companyRepository.save(company);
		return new CompanyDTO(company.getId(), company.getName());
		
	}

	public void delete(Long id) {
		companyRepository.deleteById(id);
	}

	public List<CompanyDTO> findAll() {
		return companyRepository.findAll().stream()
	            .map(company -> new CompanyDTO(company.getId(), company.getName()))
	            .collect(Collectors.toList());
	}
}
