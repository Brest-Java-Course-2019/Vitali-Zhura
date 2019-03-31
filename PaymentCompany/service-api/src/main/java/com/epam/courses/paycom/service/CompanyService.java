package com.epam.courses.paycom.service;

import com.epam.courses.paycom.model.Company;

import java.util.List;


public interface CompanyService {

    List<Company> findAll();

    void add(Company company);

    Company findById(Integer id);

    Company findByAccount(String companyAccount);

    void update(Company company);

    void delete(int id);

}


