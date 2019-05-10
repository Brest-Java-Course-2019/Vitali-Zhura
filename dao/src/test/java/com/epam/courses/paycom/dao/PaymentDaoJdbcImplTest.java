package com.epam.courses.paycom.dao;

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
import java.sql.Date;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-db.xml", "classpath:test-dao.xml"})
@Transactional
@Rollback

public class PaymentDaoJdbcImplTest {

    private static final String NEW_PAYER_NAME = "Sidoruk";
    private static final Integer NEW_PAYMENT_SUM = 2000;
    private static final String NEW_COMPANY_ACCOUNT = "BY27BLBB34325630287478004008";
    private static final String COMPANY_ACCOUNT_FALSE = "BY27BLBB34325630287478004005";
    private static final long TOTAL_COUNT = 5;
    private static final long ONE_DAY_COUNT = 3;

    @Autowired
    private PaymentDao paymentDao;

    @Test
    void findAll() {
        Stream<Payment> payments = paymentDao.findAll();
        assertNotNull(payments);
    }

    @Test
    void findAllStubs() {
        Stream<PaymentStub>stub = paymentDao.findAllStubs();
        assertNotNull(stub);
        assertTrue(stub.count() > 0);
    }

    @Test
    void findAllCheckCount() {

        Stream<Payment> payments = paymentDao.findAll();
        assertNotNull(payments);
        assertEquals(payments.count(), TOTAL_COUNT);
    }

    @Test
    void findById() {
        Payment payment = paymentDao.findById(1).get();
        assertNotNull(payment);
        assertAll(
                () -> assertEquals(payment.getPaymentId().intValue(), 1),
                () -> assertEquals(payment.getPayerName(), "Ivanov"),
                () -> assertEquals(payment.getPaymentSum().intValue(), 230),
                () -> assertEquals(payment.getCompanyAccount(), "BY27BLBB34325630287478004008"),
                () -> assertEquals(payment.getPaymentDate().toString(), "2019-03-10 12:12:30.0"));
    }

    @Test
    void findByDate() {

        Date beginDate = java.sql.Date.valueOf("2019-03-10");
        Date endDate = java.sql.Date.valueOf ("2019-03-10");

        Stream<Payment> payments= paymentDao.findByDate(beginDate, endDate);
        assertNotNull(payments);
        assertEquals(payments.count(), ONE_DAY_COUNT);
    }

    @Test
    void create() {
        Stream<Payment> companiesBeforeInsert = paymentDao.findAll();

        Payment payment = new Payment();
        payment.setPayerName(NEW_PAYER_NAME);
        payment.setPaymentSum(NEW_PAYMENT_SUM);
        payment.setCompanyAccount(NEW_COMPANY_ACCOUNT);
        Payment newPayment = paymentDao.add(payment).get();
        assertNotNull(newPayment.getPaymentId());

        Stream<Payment> companiesAfterInsert = paymentDao.findAll();
        assertEquals(1, companiesAfterInsert.count() - companiesBeforeInsert.count());
    }

    @Test
    void createPaymentWithNotExistCompany() {

        Payment payment = new Payment();
        payment.setPayerName(NEW_PAYER_NAME);
        payment.setPaymentSum(NEW_PAYMENT_SUM);
        payment.setCompanyAccount(COMPANY_ACCOUNT_FALSE);

        Assertions.assertThrows(DataAccessException.class, () -> {
                    paymentDao.add(payment).get();
                });
    }

     @Test
     void cancel () {
         Stream<Payment> paymentsBeforeDelete = paymentDao.findAll();
         Payment payment =  paymentDao.findAll().findFirst().get();
         paymentDao.cancel(payment.getPaymentId());
         Stream<Payment> paymentsAfterDelete = paymentDao.findAll();
         assertEquals(1, paymentsBeforeDelete.count() - paymentsAfterDelete.count());
     }
}
