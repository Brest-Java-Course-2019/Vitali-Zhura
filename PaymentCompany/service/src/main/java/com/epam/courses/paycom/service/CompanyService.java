
package com.epam.courses.paycom.service;

import com.epam.courses.paycom.model.Company;

import java.util.stream.Stream;

public interface CompanyService {

    Stream<Company> findAll();

    void add(Company... companies);

}


