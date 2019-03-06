package com.epam.courses.paycom.web_app.validators;

import com.epam.courses.paycom.model.Company;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CompanyValidator implements Validator {

    public static final int COMPANY_ACCOUNT_MAX_SIZE = 255;

    @Override
    public boolean supports(Class<?> clazz) {
        return Company.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "companyAccount", "companyAccount.empty");
        Company company = (Company) target;

        if (StringUtils.hasLength(company.getCompanyAccount())
                && company.getCompanyAccount().length() > COMPANY_ACCOUNT_MAX_SIZE) {
            errors.rejectValue("companyAccount", "companyAccount.maxSize255");
        }
    }
}