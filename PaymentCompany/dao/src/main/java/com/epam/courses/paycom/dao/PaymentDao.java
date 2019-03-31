package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.stub.PaymentInfo;
import com.epam.courses.paycom.stub.PaymentStub;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Stream;

public interface PaymentDao {

        Stream<Payment> findAll();

        Optional<Payment> findById(Integer paymentId);

        Stream<PaymentStub> findAllStubs();

        Stream<Payment> findByDate(Date beginDate, Date endDate);

        Optional<Payment> add(Payment payment);

        Stream<PaymentInfo> findAllInfo();

        void cancel (int paymentId);

    }

