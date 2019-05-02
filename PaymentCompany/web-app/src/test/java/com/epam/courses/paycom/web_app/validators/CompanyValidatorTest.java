package com.epam.courses.paycom.web_app.validators;

import com.epam.courses.paycom.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompanyValidatorTest {

    Company company;

    CompanyValidator companyValidator = new CompanyValidator();
    BindingResult result;

    @BeforeEach
    void setup() {
        company = Mockito.mock(Company.class);
        result = new BeanPropertyBindingResult(company, "company");
        Mockito.when(company.getCompanyAccount()).thenReturn("BY27BLBB34325630287478004008");
        Mockito.when(company.getCompanyName()).thenReturn("Prestizh");
        Mockito.when(company.getCompanyUNP()).thenReturn("200342345");
    }

    @Test
    void shouldCheckValidateCompany() {

        companyValidator.validate(company, result);

        assertFalse(result.hasErrors());
    }


    @Test
    void shouldRejectNullCompanyAccount() {
        // given
        Mockito.when(company.getCompanyAccount()).thenReturn(null);

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyCompanyAccount() {
        // given
        Mockito.when(company.getCompanyAccount()).thenReturn("");
        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectSizeCompanyAccount() {

        // given
        Mockito.when(company.getCompanyAccount()).thenReturn(StringUtils.repeat("*", 27));

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateCompanyAccount() {

        // given
        Mockito.when(company.getCompanyAccount()).thenReturn(StringUtils.repeat("*", 28));

        // when
        companyValidator.validate(company, result);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyCompanyName() {
        // given
        Mockito.when(company.getCompanyName()).thenReturn("");

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullCompanyName() {
        // given
        Mockito.when(company.getCompanyName()).thenReturn(null);

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectSizeCompanyName() {
        // given
        Mockito.when(company.getCompanyName()).thenReturn(StringUtils.repeat("*", 300));

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateCompanyName() {

        // given
        Mockito.when(company.getCompanyName()).thenReturn(StringUtils.repeat("*", 250));

        // when
        companyValidator.validate(company, result);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectNullCompanyUNP() {
        // given
        Mockito.when(company.getCompanyUNP()).thenReturn(null);

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyCompanyUNP() {
        // given
        Mockito.when(company.getCompanyUNP()).thenReturn("");

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectOnlyNumberCompanyUNP() {
        // given
        Mockito.when(company.getCompanyUNP()).thenReturn(StringUtils.repeat("*", 9));

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectSizeCompanyUNP() {
        // given
        Mockito.when(company.getCompanyUNP()).thenReturn(StringUtils.repeat("1", 10));

        // when
        companyValidator.validate(company, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateCompanyUNP() {

        // given
        Mockito.when(company.getCompanyUNP()).thenReturn(StringUtils.repeat("1", 9));

        // when
        companyValidator.validate(company, result);

        // then
        assertFalse(result.hasErrors());
    }

}
