package com.epam.courses.paycom.rest_app;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.service.PaymentService;
import com.epam.courses.paycom.stub.PaymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/payments")
public class PaymentRestController implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestController.class);


    @Autowired
    private PaymentService paymentService;

    @Override
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Payment> findAll() {
        LOGGER.debug("get all companies");
        return paymentService.findAll();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Payment findById(@PathVariable Integer id) {
        LOGGER.debug("find payment by id({})", id);
        return paymentService.findById(id);
    }

    @Override
    @RequestMapping(value = "/date", method = RequestMethod.GET)
    public List<Payment> findByDate(@PathVariable Date beginDate, Date endDate) {
        LOGGER.debug("find payment by id({})", beginDate, endDate);
        return paymentService.findByDate(beginDate, endDate);
    }

    @Override
    @RequestMapping(value = "/stub", method = RequestMethod.GET)
    public List <PaymentInfo> findAllStubs() {
        LOGGER.debug("find all stubs");
        return paymentService.findAllStubs();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Payment payment) {
        LOGGER.debug("add payment({})", payment);
        paymentService.add(payment);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void cancel(@PathVariable("id") int id) {
        LOGGER.debug("delete payment ({})", id);
        paymentService.cancel(id);
    }

}
