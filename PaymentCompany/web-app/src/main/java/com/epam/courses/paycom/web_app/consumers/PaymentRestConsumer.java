package com.epam.courses.paycom.web_app.consumers;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.stub.PaymentInfo;
import com.epam.courses.paycom.service.PaymentService;
import com.epam.courses.paycom.stub.PaymentStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.List;


public class PaymentRestConsumer implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestConsumer.class);

    private String url;

    private RestTemplate restTemplate;

    public PaymentRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Payment> findAll() {
        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all", List.class);
        return (List<Payment>) responseEntity.getBody();
    }

    @Override
    public Payment findById(Integer id) {
        LOGGER.debug("findById({})", id);
        ResponseEntity<Payment> responseEntity = restTemplate.getForEntity(url + "/" + id, Payment.class);
        return responseEntity.getBody();
    }

    @Override
    public  List<PaymentInfo> findAllInfo() {
        LOGGER.debug("findAllInfo({})");
        ResponseEntity<Payment> responseEntity = restTemplate.getForEntity(url + "/info", Payment.class);
        return (List<PaymentInfo>) responseEntity.getBody();
    }

    @Override
    public  List<PaymentStub> findAllStubs() {
        LOGGER.debug("findAllStubs({})");
        ResponseEntity<Payment> responseEntity = restTemplate.getForEntity(url + "/stubs", Payment.class);
        return (List<PaymentStub>) responseEntity.getBody();
    }

    @Override
    public void add(Payment payment) {
        LOGGER.debug("add({})", payment);
        restTemplate.postForEntity(url, payment, Payment.class);
    }

    @Override
    public List<Payment> findByDate(Date beginDate, Date endDate) {
        LOGGER.debug("findByDate()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/date", List.class);
        return (List<Payment>) responseEntity.getBody();
    }

    @Override
    public void cancel(int id) {
        LOGGER.debug("cancel({})", id);
        restTemplate.delete(url + "/" + id);
    }
}