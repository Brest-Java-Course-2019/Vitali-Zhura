package com.epam.courses.paycom.service;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.stub.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath:test-service.xml"})

public class PaymentServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentService paymentService;

    @Test
    void findAll() {
        List<Payment> payments = paymentService.findAll();
        assertNotNull(payments);
    }

    @Test
    void findAllInfo() {
        List<PaymentInfo> payments = paymentService.findAllInfo();
        assertNotNull(payments);
    }

    @Test
    void findAllStubs() {
        List<PaymentStub> payments = paymentService.findAllStubs();
        assertNotNull(payments);
    }

    @Test
    void findById() {

        Payment firstPayment = paymentService.findAll().get(0);
        assertNotNull(firstPayment);
        Integer id = firstPayment.getPaymentId();

        Payment payment = paymentService.findById(id);
        assertNotNull(payment);
        assertEquals(firstPayment.getCompanyAccount(), payment.getCompanyAccount());
    }


    @Test
    void add() {

        long count = paymentService.findAll().size();
        LOGGER.debug("Count before: {}", count);

        paymentService.add(create());

        long newCount = paymentService.findAll().size();
        LOGGER.debug("Count after: {}", newCount);
        assert count == newCount-1;
    }

    private Payment create() {
        Payment payment = new Payment();
        payment.setPayerName("payer");
        payment.setPaymentSum(100);
        payment.setCompanyAccount("BY27BLBB37899630217778006009");
        return payment;
    }

    @Test
    void cancel() {

        long count = paymentService.findAll().size();
        LOGGER.debug("Count before: {}", count);

        paymentService.cancel(paymentService.findAll().get(2).getPaymentId());
        long newCount = paymentService.findAll().size();
        LOGGER.debug("Count after: {}", newCount);
        assert count == newCount+1;
    }
}
