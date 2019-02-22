package com.epam.courses.paycom.dao;


import com.epam.courses.paycom.model.Company;

import java.util.Optional;
import java.util.stream.Stream;


public interface CompanyDao {

    Stream<Company> findAll();

    Optional <Company> findById(Integer companyId);

    Optional<Company> add(Company company);

    void update(Company company);

    void delete(int companyId);

}
