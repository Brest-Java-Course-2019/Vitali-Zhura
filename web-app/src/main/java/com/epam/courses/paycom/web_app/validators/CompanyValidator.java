package com.epam.courses.paycom.web_app.validators;

import com.epam.courses.paycom.model.Company;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CompanyValidator implements Validator {

    public static final int COMPANY_ACCOUNT_SIZE = 28;
    public static final int COMPANY_NAME_MAX_SIZE = 255;
    public static final int COMPANY_UNP_SIZE = 9;
    public static final String  NUMBER = "[0-9]+";

    @Override
    public boolean supports(Class<?> clazz) {
        return Company.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "companyAccount", "companyAccount.empty");
        ValidationUtils.rejectIfEmpty(errors, "companyName", "companyName.empty");
        ValidationUtils.rejectIfEmpty(errors, "companyUNP", "companyUNP.empty");
        Company company = (Company) target;

        if (StringUtils.hasLength(company.getCompanyAccount())
                && company.getCompanyAccount().length() != COMPANY_ACCOUNT_SIZE) {
            errors.rejectValue("companyAccount", "companyAccount.Size28");
        }

        if (StringUtils.hasLength(company.getCompanyName())
                && company.getCompanyName().length() > COMPANY_NAME_MAX_SIZE) {
            errors.rejectValue("companyName", "companyName.maxSize255");
        }

        if (StringUtils.hasLength(company.getCompanyUNP())
                && company.getCompanyUNP().length() != COMPANY_UNP_SIZE)   {
            errors.rejectValue("companyUNP", "companyUNP.Size9");
        }

        if (StringUtils.hasLength(company.getCompanyUNP())
               && !company.getCompanyUNP().matches(NUMBER)) {
            errors.rejectValue("companyUNP", "companyUNP.Number");
        }
    }
}