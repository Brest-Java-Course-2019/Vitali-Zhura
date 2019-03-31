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
    import org.opentest4j.AssertionFailedError;
    import org.opentest4j.MultipleFailuresError;
    import java.util.stream.Stream;
    import static org.junit.jupiter.api.Assertions.*;

    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
    @Transactional
    @Rollback

    public class CompanyDaoJdbcImplTest {

        private static final String NEW_COMPANY_ACCOUNT = "New Company Account";
        private static final String NEW_COMPANY_NAME = "New Company Name";
        private static final Integer NEW_COMPANY_UNP = 100000000;
        private static final long TOTAL_COUNT = 4;

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
            assertEquals(companies.count(), TOTAL_COUNT);
        }

        @Test
        void incorrectFindAllCheckCount() {

            Stream<Company> companies = companyDao.findAll();
            assertNotNull(companies);
            Assertions.assertThrows(AssertionFailedError.class, () -> {
            assertEquals(companies.count(), TOTAL_COUNT-1);});
        }

        @Test
        void findById() {
            Company company = companyDao.findById(1).get();
            assertNotNull(company);
            assertAll(
            () -> assertEquals(company.getCompanyId().intValue(), 1),
            () -> assertEquals(company.getCompanyAccount(), "BY27BLBB34325630287478004008"),
            () -> assertEquals(company.getCompanyName(), "Престиж"),
            () -> assertEquals(company.getCompanyUNP().intValue(), 200342345));
        }

        @Test
        void incorrectFindById() {
            Company company = companyDao.findById(1).get();
            assertNotNull(company);
            Assertions.assertThrows(MultipleFailuresError.class, () ->
                    assertAll(
                    () -> assertEquals(company.getCompanyId().intValue(), 1),
                    () -> assertEquals(company.getCompanyAccount(), "BY27BLBB34325630287478004009"),
                    () -> assertEquals(company.getCompanyName(), "ПрестижПром"),
                    () -> assertEquals(company.getCompanyUNP().intValue(), 200342346)));
        }

        @Test
        void findByAccount() {
            Company company = companyDao.findByAccount("BY27BLBB34325630287478004008").get();
            assertNotNull(company);
            assertAll(
                    () -> assertEquals(company.getCompanyId().intValue(), 1),
                    () -> assertEquals(company.getCompanyName(), "Престиж"),
                    () -> assertEquals(company.getCompanyUNP().intValue(), 200342345));
        }

        @Test
        void incorrectFindByAccount() {
            Company company = companyDao.findByAccount("BY27BLBB34325630287478004008").get();
            assertNotNull(company);
            Assertions.assertThrows(MultipleFailuresError.class, () ->
            assertAll(
                    () -> assertEquals(company.getCompanyId().intValue(), 2),
                    () -> assertEquals(company.getCompanyName(), "Престип"),
                    () -> assertEquals(company.getCompanyUNP().intValue(), 20034234)));
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
            assertEquals(1, companiesAfterInsert.count() - companiesBeforeInsert.count());
        }

        @Test
        void incorrectCreate() {
            Stream<Company> companiesBeforeInsert = companyDao.findAll();

            Company company = new Company();
            company.setCompanyAccount(NEW_COMPANY_ACCOUNT);
            company.setCompanyName(NEW_COMPANY_NAME);
            company.setCompanyUNP(NEW_COMPANY_UNP);
            Company newCompany = companyDao.add(company).get();
            assertNotNull(newCompany.getCompanyId());

            Stream<Company> companiesAfterInsert = companyDao.findAll();
            Assertions.assertThrows(AssertionFailedError.class, () -> {
            assertEquals(0, companiesAfterInsert.count() - companiesBeforeInsert.count());});
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

            Stream<Company> companiesBeforeDelete = companyDao.findAll();
            Company company =  companyDao.findById(3).get();
            companyDao.delete(company.getCompanyId());
            Stream<Company> companiesAfterDelete = companyDao.findAll();
            assertEquals(1, companiesBeforeDelete.count() - companiesAfterDelete.count());
        }

        @Test
        void incorrectdelete() {
            Company company = companyDao.findById(2).get();

            Assertions.assertThrows(DataAccessException.class, () -> {
                companyDao.delete(company.getCompanyId());
            });
        }

    }
