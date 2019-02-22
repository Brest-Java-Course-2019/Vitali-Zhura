    package com.epam.courses.paycom.dao;

    import com.epam.courses.paycom.model.Company;

    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.DataAccessException;
    import org.springframework.test.context.ContextConfiguration;
    import org.springframework.test.context.junit.jupiter.SpringExtension;
    import org.springframework.test.annotation.Rollback;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.stream.Stream;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
    @Transactional
    @Rollback

public class CompanyDaoImplTest {

    private static final String NEW_COMPANY_ACCOUNT = "New Company";
    private static final String NEW_COMPANY_NAME = "New Company Name";


    @Autowired
    private CompanyDao companyDao;

    @Test
    void findAll() {

        Stream<Company> companies = companyDao.findAll();
        assertNotNull(companies);
    }

    @Test
    void findAllCheckCount() {

        Stream<Company> companies = companyDao.findAll();
        assertNotNull(companies);
        assertEquals(companies.count(), 4);
    }

    @Test
    void findById() {

        Company company = companyDao.findById(1).get();
        assertNotNull(company);
        assertEquals(company.getCompanyId().intValue(), 1);
        assertEquals(company.getCompanyAccount(), "A567");
    }

    @Test
        void create() {
        Stream<Company> companiesBeforeInsert = companyDao.findAll();

        Company company = new Company();
        company.setCompanyAccount(NEW_COMPANY_ACCOUNT);
        company.setCompanyName(NEW_COMPANY_NAME);
        Company newCompany = companyDao.add(company).get();
        assertNotNull(newCompany.getCompanyId());

        Stream<Company> companiesAfterInsert = companyDao.findAll();
        assertEquals(1, companiesAfterInsert.count() - companiesBeforeInsert.count());
        }

        @Test
        void createDuplicateCompany() {
        Company company2 = new Company();
        company2.setCompanyAccount(NEW_COMPANY_ACCOUNT);
        company2.setCompanyName(NEW_COMPANY_NAME);
        Company newCompany = companyDao.add(company2).get();
        assertNotNull(newCompany.getCompanyId());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            companyDao.add(company2);
            });
        }

        @Test
        void update() {
        Company company = new Company();
        company.setCompanyAccount(NEW_COMPANY_ACCOUNT);
        company.setCompanyName(NEW_COMPANY_ACCOUNT);
        Company newCompany = companyDao.add(company).get();
        assertNotNull(newCompany.getCompanyId());

        company.setCompanyAccount(NEW_COMPANY_ACCOUNT + "_2");
        company.setCompanyName(NEW_COMPANY_ACCOUNT + "_2");
        companyDao.update(company);

        Company updatedCompany = companyDao.findById(company.getCompanyId()).get();

        assertEquals(NEW_COMPANY_ACCOUNT + "_2", updatedCompany.getCompanyAccount());
        assertEquals(NEW_COMPANY_ACCOUNT + "_2", updatedCompany.getCompanyName());
        }

        @Test
        void delete() {
        Stream<Company> companies = companyDao.findAll();
        Company company = companies.findFirst().get();
        companyDao.delete(company.getCompanyId());

        Assertions.assertThrows(DataAccessException.class, () -> {
            companyDao.findById(company.getCompanyId());
            });
        }

}
