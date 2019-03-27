package com.epam.courses.paycom.service;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath:test-service.xml"})
class CompanyServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyService companyService;

    @Test
    void findAll() {
        List<Company> companies = companyService.findAll();
        assertNotNull(companies);
    }


    @Test
    void add() {

        long count = companyService.findAll().size();
        LOGGER.debug("Count before: {}", count);

        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            companyService.add(companyService.findAll().get(0));
        });

        long newCount = companyService.findAll().size();
        LOGGER.debug("Count after: {}", newCount);
        assert count == newCount;
    }


    private Company create() {
        Company company = new Company();
        company.setCompanyAccount("account");
        company.setCompanyName("name");
        company.setCompanyUNP(124004567);
        return company;
    }

    @Test
    void findById() {

        Company firstCompany = companyService.findAll().get(0);
        assertNotNull(firstCompany);
        Integer id = firstCompany.getCompanyId();

        Company company = companyService.findById(id);
        assertNotNull(company);
        assertEquals(firstCompany.getCompanyAccount(), company.getCompanyAccount());
    }

    @Test
    void findByAccount() {

        Company secondCompany = companyService.findAll().get(2);
        assertNotNull(secondCompany);
        String account = secondCompany.getCompanyAccount();

        Company company = companyService.findByAccount(account);
        assertNotNull(company);
        assertEquals(secondCompany.getCompanyName(), company.getCompanyName());
        assertEquals(secondCompany.getCompanyUNP(), company.getCompanyUNP());
    }

    @Test
    void delete() {

        long count = companyService.findAll().size();
        LOGGER.debug("Count before: {}", count);

        companyService.delete(companyService.findAll().get(0).getCompanyId());
        long newCount = companyService.findAll().size();
        LOGGER.debug("Count after: {}", newCount);
        assert count == newCount+1;

    }

    @Test
    void update() {
        Company afterUpdate = companyService.findAll().get(0);
        afterUpdate.setCompanyAccount("new Account");
        afterUpdate.setCompanyName("new Name");
        afterUpdate.setCompanyUNP(1111);
        companyService.update(afterUpdate);
        assertEquals(companyService.findAll().get(0).getCompanyId(), afterUpdate.getCompanyId());



    }
}