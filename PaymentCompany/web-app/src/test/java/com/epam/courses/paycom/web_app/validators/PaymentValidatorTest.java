package com.epam.courses.paycom.web_app.validators;

import com.epam.courses.paycom.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentValidatorTest {

    Payment payment;

    PaymentValidator paymentValidator = new PaymentValidator();
    BindingResult result;

    @BeforeEach
    void setup() {
        payment = Mockito.mock(Payment.class);
        result = new BeanPropertyBindingResult(payment, "payment");
        Mockito.when(payment.getCompanyAccount()).thenReturn("BY27BLBB34325630287478004008");
        Mockito.when(payment.getPayerName()).thenReturn("Ivanov");
        Mockito.when(payment.getPaymentSum()).thenReturn(100);
    }

    @Test
    void shouldCheckValidatePayment() {

        paymentValidator.validate(payment, result);

        assertFalse(result.hasErrors());
    }


    @Test
    void shouldRejectNullCompanyAccount() {
        // given
        Mockito.when(payment.getCompanyAccount()).thenReturn(null);

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyCompanyAccount() {
        // given
        Mockito.when(payment.getCompanyAccount()).thenReturn("");

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectSizeCompanyAccount() {

        // given
        Mockito.when(payment.getCompanyAccount()).thenReturn(StringUtils.repeat("*", 27));

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateCompanyAccount() {

        // given
        Mockito.when(payment.getCompanyAccount()).thenReturn(StringUtils.repeat("*", 28));

        // when
        paymentValidator.validate(payment, result);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyPayerName() {
        // given
        Mockito.when(payment.getPayerName()).thenReturn("");

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullPayerName() {
        // given
        Mockito.when(payment.getPayerName()).thenReturn(null);

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectSizePayerName() {
        // given
        Mockito.when(payment.getPayerName()).thenReturn(StringUtils.repeat("*", 300));

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidatePayerName() {

        // given
        Mockito.when(payment.getPayerName()).thenReturn(StringUtils.repeat("*", 250));

        // when
        paymentValidator.validate(payment, result);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectNullPaymentSum() {
        // given
        Mockito.when(payment.getPaymentSum()).thenReturn(null);

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectValuePaymentSum() {
        // given
        Mockito.when(payment.getPaymentSum()).thenReturn(0);

        // when
        paymentValidator.validate(payment, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidatePaymentSum() {

        // given
        Mockito.when(payment.getPaymentSum()).thenReturn(5);

        // when
        paymentValidator.validate(payment, result);

        // then
        assertFalse(result.hasErrors());
    }

}
