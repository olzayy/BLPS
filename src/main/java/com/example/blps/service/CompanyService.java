package com.example.blps.service;

import com.example.blps.module.entity.Company;

import java.util.List;

public interface CompanyService {

	List<Company> findAllCompaniesByEmail(String email);

	void addNewCompanyWithEmail(Company company);

	List<Company> findAllbyParams(String org_name, String inn, String ogrn);

	List<Company> findAllUnacceptable(String email);

	Company findCompanyByInn(String inn);

	void updateCompanyByInn(String inn, Integer rate);
}
