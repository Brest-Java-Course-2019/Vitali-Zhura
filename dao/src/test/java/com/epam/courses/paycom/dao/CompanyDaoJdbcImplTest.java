    package com.epam.courses.paycom.dao;

    import com.epam.courses.paycom.model.Company;

    import com.epam.courses.paycom.stub.CompanyStub;
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.DataAccessException;
    import org.springframework.dao.DuplicateKeyException;
    import org.springframework.test.context.ContextConfiguration;
    import org.springframework.test.context.junit.jupiter.SpringExtension;
    import org.springframework.test.annotation.Rollback;
    import org.springframework.transaction.annotation.Transactional;
    import java.util.stream.Stream;
    import static org.junit.jupiter.api.Assertions.*;

    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
    @Transactional
    @Rollback

    public class CompanyDaoJdbcImplTest {

        private static final String NEW_COMPANY_ACCOUNT = "New Company Account";
        private static final String NEW_COMPANY_NAME = "New Company Name";
        private static final String NEW_COMPANY_UNP = "100000000";
        private static final String FIRST_COMPANY_ACCOUNT = "BY27BLBB34325630287478004008";
        private static final String FIRST_COMPANY_NAME = "Prestizh";
        private static final String FIRST_COMPANY_UNP = "200342345";
        private static final long TOTAL_COUNT = 4;
        private static final int ONE = 1;

        @Autowired
        private CompanyDao companyDao;

        @Test
        void findAll() {

            Stream<Company> companies = companyDao.findAll();
            assertNotNull(companies);
        }

        @Test
        void findAllStubs() {
            Stream<CompanyStub>stub = companyDao.findAllStubs();
            assertNotNull(stub);
            assertTrue(stub.count() > 0);
        }

        @Test
        void findAllCheckCount() {

            Stream<Company> companies = companyDao.findAll();
            assertNotNull(companies);
            assertEquals(companies.count(), TOTAL_COUNT);
        }

        @Test
        void findById() {
            Company company = companyDao.findById(ONE).get();
            assertNotNull(company);
            assertAll(
            () -> assertEquals(company.getCompanyId().intValue(), ONE),
            () -> assertEquals(company.getCompanyAccount(), FIRST_COMPANY_ACCOUNT),
            () -> assertEquals(company.getCompanyName(), FIRST_COMPANY_NAME),
            () -> assertEquals(company.getCompanyUNP(), FIRST_COMPANY_UNP));
        }

        @Test
        void findByAccount() {
            Company company = companyDao.findByAccount(FIRST_COMPANY_ACCOUNT).get();
            assertNotNull(company);
            assertAll(
                    () -> assertEquals(company.getCompanyId().intValue(), ONE),
                    () -> assertEquals(company.getCompanyName(), FIRST_COMPANY_NAME),
                    () -> assertEquals(company.getCompanyUNP(), FIRST_COMPANY_UNP));
        }

        @Test
        void create() {
            Stream<Company> companiesBeforeInsert = companyDao.findAll();

            Company company = new Company();
            company.setCompanyAccount(NEW_COMPANY_ACCOUNT);
            company.setCompanyName(NEW_COMPANY_NAME);
            company.setCompanyUNP(NEW_COMPANY_UNP);
            Company newCompany = companyDao.add(company).get();
            assertNotNull(newCompany.getCompanyId());

            Stream<Company> companiesAfterInsert = companyDao.findAll();
            assertEquals(ONE, companiesAfterInsert.count() - companiesBeforeInsert.count());
        }

        @Test
        void createDuplicateCompany() {
            Company company2 = new Company();
            company2.setCompanyAccount(NEW_COMPANY_ACCOUNT);
            company2.setCompanyName(NEW_COMPANY_NAME);
            Company newCompany = companyDao.add(company2).get();
            assertNotNull(newCompany.getCompanyId());

            Assertions.assertThrows(DuplicateKeyException.class, () -> {
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

            Stream<Company> companiesBeforeDelete = companyDao.findAll();
            Company company =  companyDao.findById(3).get();
            companyDao.delete(company.getCompanyId());
            Stream<Company> companiesAfterDelete = companyDao.findAll();
            assertEquals(1, companiesBeforeDelete.count() - companiesAfterDelete.count());
        }

        @Test
        void deleteUsingCompany() {
            Company company = companyDao.findById(2).get();

            Assertions.assertThrows(DataAccessException.class, () -> {
                companyDao.delete(company.getCompanyId());
            });
        }
    }
