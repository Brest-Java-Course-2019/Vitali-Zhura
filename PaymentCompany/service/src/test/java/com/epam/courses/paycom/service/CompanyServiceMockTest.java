package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.CompanyDao;
import com.epam.courses.paycom.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CompanyServiceMockTest {

    private CompanyDao dao;

    private CompanyService service;

    @BeforeEach
    void setup() {
        dao = Mockito.mock(CompanyDao.class);
        service = new CompanyServiceImpl(dao);
    }

    @Test
    public void findAll() {

        Mockito.when(dao.findAll()).thenReturn(Stream.of(create()));

        Stream<Company> result = service.findAll();
        assertNotNull(result);
        assertEquals(1, result.count());

        Mockito.verify(dao, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(dao);
    }

    private Company create() {
        Company company = new Company();
        company.setCompanyAccount("account");
        company.setCompanyName("name");
        return company;
    }
}