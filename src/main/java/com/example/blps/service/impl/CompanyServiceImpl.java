package com.example.blps.service.impl;

import com.example.blps.exceptions.ResourceNotFoundException;
import com.example.blps.module.Company;
import com.example.blps.module.User;
import com.example.blps.repo.CompanyRepository;
import com.example.blps.service.CompanyService;
import com.example.blps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Company> findAllCompaniesByEmail(String email) {
        return companyRepository.findAllByUserEmail(email);
    }

    @Override
    @Transactional
    public void addNewCompanyWithEmail(Company company) {
        User user = userService.findUserByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Пользователь не найден"));

        user.getCompanies().add(company);
        company.setUser(user);
    }

    @Override
    public List<Company> findAllbyParams(Optional<String> org_name, Optional<String> inn, Optional<String> ogrn) {
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
