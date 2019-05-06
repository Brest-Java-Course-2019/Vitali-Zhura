package com.epam.courses.paycom.web_app.validators;

import com.epam.courses.paycom.model.Payment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PaymentValidator implements Validator{

    public static final int COMPANY_ACCOUNT_SIZE = 28;
    public static final int PAYER_NAME_MAX_SIZE = 255;

    @Override
    public boolean supports(Class<?> clazz) {
        return Payment.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "companyAccount", "companyAccount.empty");
        ValidationUtils.rejectIfEmpty(errors, "payerName", "payerName.empty");
        Payment payment = (Payment) target;

        if (StringUtils.hasLength(payment.getCompanyAccount())
                && payment.getCompanyAccount().length() != COMPANY_ACCOUNT_SIZE) {
            errors.rejectValue("companyAccount", "companyAccount.Size28");
        }

        if (StringUtils.hasLength(payment.getPayerName())
                && payment.getPayerName().length() > PAYER_NAME_MAX_SIZE) {
            errors.rejectValue("payerName", "payerName.maxSize255");
        }

        if (payment.getPaymentSum()==null || payment.getPaymentSum() <= 0) {
            errors.rejectValue("paymentSum", "paymentSum.Value");
        }

        }

    }

