package com.example.blps.service;

import com.example.blps.module.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

	List<Company> findAllCompaniesByEmail(String email);

	void addNewCompanyWithEmail(Company company);

	List<Company> findAllbyParams(Optional<String> org_name, Optional<String> inn, Optional<String> ogrn);

	List<Company> findAllUnacceptable(String email);

	Company findCompanyByInn(String inn);

	void updateCompanyByInn(String inn, Integer rate);
}
