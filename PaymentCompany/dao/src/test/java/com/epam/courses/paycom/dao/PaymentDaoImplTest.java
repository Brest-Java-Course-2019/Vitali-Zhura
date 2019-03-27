package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.model.Payment;

import com.epam.courses.paycom.stub.PaymentStub;
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

import java.sql.Date;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
@Transactional
@Rollback

public class PaymentDaoImplTest {

    private static final String NEW_PAYER_NAME = "Sidoruk";
    private static final String NEW_PAYMENT_DESC = "Uslugi";
    private static final Integer NEW_PAYMENT_SUM = 2000;
    private static final Integer NEW_COMPANY_ID = 3;


    @Autowired
    private PaymentDao paymentDao;

    @Test
    void findAll() {
        Stream<Payment> payments = paymentDao.findAll();
        assertNotNull(payments);
    }

    @Test
    void findAllCheckCount() {

        Stream<Payment> payments = paymentDao.findAll();
        assertNotNull(payments);
        assertEquals(payments.count(), 5);
    }

    @Test
    void findAllStubs() {
        Stream<PaymentStub>stub = paymentDao.findAllStubs();
        assertNotNull(stub);
        assertTrue(stub.count() > 0);
    }

    @Test
    void findById() {
        Payment payment = paymentDao.findById(1).get();
        assertNotNull(payment);
        assertAll(
                () -> assertEquals(payment.getPaymentId().intValue(), 1),
                () -> assertEquals(payment.getPayerName(), "Ivanov"),
                () -> assertEquals(payment.getPaymentDescription(), "Za vodu"),
                () -> assertEquals(payment.getPaymentSum().intValue(), 230),
                () -> assertEquals(payment.getCompanyId().intValue(), 1),
                () -> assertEquals(payment.getPaymentDate().toString(), "2019-03-10 12:12:30.0"));
    }

    @Test
    void findByDate() {

        Date beginDate = java.sql.Date.valueOf("2019-03-10");
        Date endDate = java.sql.Date.valueOf ("2019-03-12");

        Stream<Payment> payments= paymentDao.findByDate(beginDate, endDate);
        assertNotNull(payments);
        assertEquals(payments.count(), 5);
    }


    @Test
    void create() {
        Stream<Payment> companiesBeforeInsert = paymentDao.findAll();

        Payment payment = new Payment();
        payment.setPayerName(NEW_PAYER_NAME);
        payment.setPaymentDescription(NEW_PAYMENT_DESC);
        payment.setPaymentSum(NEW_PAYMENT_SUM);
        payment.setCompanyId(NEW_COMPANY_ID);
        Payment newPayment = paymentDao.add(payment).get();
        assertNotNull(newPayment.getPaymentId());

        Stream<Payment> companiesAfterInsert = paymentDao.findAll();
        assertEquals(1, companiesAfterInsert.count() - companiesBeforeInsert.count());

    }

    @Test
    void cancel () {
        Stream<Payment> payments = paymentDao.findAll();
        Payment payment = payments.findFirst().get();
        paymentDao.cancel(payment.getPaymentId());

        Assertions.assertThrows(DataAccessException.class, () -> {
            paymentDao.findById(payment.getPaymentId());
        });
    }
}
