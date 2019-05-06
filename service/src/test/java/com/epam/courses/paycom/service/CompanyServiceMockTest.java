package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.CompanyDao;
import com.epam.courses.paycom.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

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

        List<Company> result = service.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());

        Mockito.verify(dao, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void findById() {

        Mockito.when(dao.findById(anyInt())).thenReturn(Optional.of(create()));

        Company result = service.findById(1);
        assertNotNull(result);

        Mockito.verify(dao, Mockito.times(1)).findById(anyInt());
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void findByAccount() {

        Mockito.when(dao.findByAccount(anyString())).thenReturn(Optional.of(create()));

        Company result = service.findByAccount(anyString());
        assertNotNull(result);

        Mockito.verify(dao, Mockito.times(1)).findByAccount(anyString());
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void add() {

        Mockito.when(dao.add(any())).thenReturn(Optional.of(create()));

        service.add(any());
        Mockito.verify(dao, Mockito.times(1)).add(any());
        Mockito.verifyNoMoreInteractions(dao);

    }


    private Company create() {
        Company company = new Company();
        company.setCompanyId(5);
        company.setCompanyAccount("account");
        company.setCompanyName("name");
        company.setCompanyUNP("111111111");
        return company;
    }




}