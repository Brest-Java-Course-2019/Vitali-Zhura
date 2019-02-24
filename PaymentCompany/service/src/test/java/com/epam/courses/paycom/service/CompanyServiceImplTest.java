package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.CompanyDao;
import com.epam.courses.paycom.model.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath:test-service.xml"})
class CompanyServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyService companyService;

    @Test
    void findAll() {
        Stream<Company> departments = companyService.findAll();
        assertNotNull(departments);
    }

    @Test
    void add() {

        long count = companyService.findAll().count();
        LOGGER.debug("Count before: {}", count);

        Company company = create();

        long newCount = companyService.findAll().count();
        LOGGER.debug("Count after: {}", newCount);
        assert count == newCount;
    }

    private Company create() {
        Company company = new Company();
        company.setCompanyAccount("account");
        company.setCompanyName("name");
        return company;
    }
}