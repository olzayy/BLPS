package com.example.blps.service.impl;

import com.example.blps.exceptions.ResourceNotFoundException;
import com.example.blps.module.entity.Company;
import com.example.blps.module.entity.User;
import com.example.blps.repo.CompanyRepository;
import com.example.blps.service.CompanyService;
import com.example.blps.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	private final UserService userService;

	@Override
	public List<Company> findAllCompaniesByEmail(String email) {
		return companyRepository.findAllByUserEmail(email);
	}

	@Override
	@Transactional
	public void addNewCompanyWithEmail(Company company) {
		User user = userService.findUserByEmail(
				((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("Error: Пользователь не найден"));

		user.getCompanies().add(company);
		company.setUser(user);
	}

	@Override
	public List<Company> findAllbyParams(String org_name, String inn, String ogrn) {
		return companyRepository.findAllByOrg_nameOrInnOrOgrn(org_name, inn, ogrn);
	}

	@Override
	public List<Company> findAllUnacceptable(String email) {
		return companyRepository.findAllUnacceptable(email);
	}

	@Override
	public Company findCompanyByInn(String inn) {
		return companyRepository.findCompanyByInn(inn);
	}

	@Override
	public void updateCompanyByInn(String inn, Integer rate) {
		companyRepository.updateCompanyByInn(inn, rate);
	}
}
