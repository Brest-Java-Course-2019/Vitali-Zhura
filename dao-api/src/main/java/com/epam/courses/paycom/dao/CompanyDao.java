package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.stub.CompanyStub;

import java.util.Optional;
import java.util.stream.Stream;

public interface CompanyDao {

    Stream<Company> findAll();

    Optional <Company> findById(Integer companyId);

    Optional <Company> findByAccount(String companyAccount);

    Stream<CompanyStub> findAllStubs();

    Optional<Company> add(Company company);

    Optional<Company> addCompany(Company company);

    void update(Company company);

    void delete(int companyId);

}
