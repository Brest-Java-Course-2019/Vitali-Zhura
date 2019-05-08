package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.CompanyDao;
import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.stub.CompanyStub;
import org.junit.jupiter.api.BeforeAll;
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

    private static final int ONE = 1;

    private static Company FIRST_COMPANY;
    private static CompanyStub FIRST_COMPANYSTUB;

    @BeforeAll
    static void init() {

        FIRST_COMPANY = create(ONE);
        FIRST_COMPANYSTUB = createStub(ONE);
    }

    @BeforeEach
    void setup() {
        dao = Mockito.mock(CompanyDao.class);
        service = new CompanyServiceImpl(dao);
    }

    @Test
    public void findAll() {

        Mockito.when(dao.findAll()).thenReturn(Stream.of(FIRST_COMPANY));

        List<Company> result = service.findAll();
        assertNotNull(result);
        assertEquals(ONE, result.size());

        Mockito.verify(dao, Mockito.times(ONE)).findAll();
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void findAllStubs() {

        Mockito.when(dao.findAllStubs()).thenReturn(Stream.of(FIRST_COMPANYSTUB));

        List<CompanyStub> result = service.findAllStubs();
        assertNotNull(result);
        assertEquals(ONE, result.size());

        Mockito.verify(dao, Mockito.times(ONE)).findAllStubs();
        Mockito.verifyNoMoreInteractions(dao);
    }


    @Test
    public void findById() {

        Mockito.when(dao.findById(anyInt())).thenReturn(Optional.of(FIRST_COMPANY));

        Company result = service.findById(ONE);
        assertNotNull(result);
        assertEquals(result.getCompanyId(), FIRST_COMPANY.getCompanyId());
        Mockito.verify(dao, Mockito.times(ONE)).findById(anyInt());
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void findByAccount() {

        Mockito.when(dao.findByAccount(anyString())).thenReturn(Optional.of(FIRST_COMPANY));

        Company result = service.findByAccount(anyString());
        assertNotNull(result);

        Mockito.verify(dao, Mockito.times(ONE)).findByAccount(anyString());
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void add() {

        Mockito.when(dao.add(any())).thenReturn(Optional.of(FIRST_COMPANY));

        service.add(any());
        Mockito.verify(dao, Mockito.times(ONE)).add(any());
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void update() {

        dao.update(any());
        Mockito.verify(dao, Mockito.times(ONE)).update(any());
        Mockito.verifyNoMoreInteractions(dao);
    }

    @Test
    public void delete() {

        dao.delete(anyInt());
        Mockito.verify(dao, Mockito.times(ONE)).delete(anyInt());
        Mockito.verifyNoMoreInteractions(dao);
    }

    private static Company create(int index) {
        Company company = new Company();
        company.setCompanyId(index);
        company.setCompanyAccount("account" + index);
        company.setCompanyName("name" + index);
        company.setCompanyUNP("11111111" + index);
        return company;
    }

    private static CompanyStub createStub(int index) {
        CompanyStub companyStub = new CompanyStub();
        companyStub.setId(index);
        companyStub.setCompany("company" + index);
        companyStub.setCount(5 + index);
        companyStub.setAmounts(200 + index);
        return companyStub;
    }
}