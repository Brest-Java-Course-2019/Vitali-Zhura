package com.epam.courses.paycom.web_app;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.service.PaymentService;
import com.epam.courses.paycom.web_app.validators.PaymentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.sql.Date;

import javax.validation.Valid;

@Controller
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    PaymentValidator paymentValidator;

    public PaymentController (PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/payments")
    public final String payments(Model model) {
        LOGGER.debug("findAll({})", model);
        model.addAttribute("payments", paymentService.findAll());
        return "payments";
    }

    @GetMapping(value = "/paymentsStub")
    public final String paymentsStub(Model model) {
        LOGGER.debug("findAllStubs({})", model);
        model.addAttribute("paymentsStub", paymentService.findAllStubs());
        return "paymentsStub";
    }

    @GetMapping(value = "/payments/{beginDate}/{endDate}")
    public final String paymentsDate(@PathVariable Date beginDate, @PathVariable Date endDate, Model model) {
        LOGGER.debug("findByDate({}, {})", beginDate, endDate, model);
        model.addAttribute("isNew", false);
        model.addAttribute("payments", paymentService.findByDate(beginDate, endDate));
        return "payments";
    }

    @GetMapping(value = "/payment")
    public String payment(Model model) {
        Payment payment = new Payment();
        model.addAttribute("isNew", true);
        model.addAttribute("payment", payment);
        return "payment";
    }

    @PostMapping(value = "/payment")
    public String addPayment(@Valid Payment payment,
                             BindingResult result) {
        LOGGER.debug("addPayment({})", payment);
        paymentValidator.validate(payment, result);
        if (result.hasErrors()) {
            return "payment";
        } else {
            this.paymentService.add(payment);
        }
        return "redirect:/payments";
    }


    @GetMapping(value = "/payment/{id}")
    public String gotoEditPaymentPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditPaymentPage({}, {})", id, model);
        model.addAttribute("isNew", false);
        Payment payment = paymentService.findById(id);
        model.addAttribute("payment", payment);

        return "payment";
    }

    @GetMapping(value = "/payment/{id}/delete")
    public final String cancelPaymentById(@PathVariable Integer id, Model model) {
        LOGGER.debug("cancel({},{})", id, model);
        paymentService.cancel(id);
        return "redirect:/paymentsStub";
    }
}
