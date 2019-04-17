package com.epam.courses.paycom.web_app;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.service.PaymentService;
import com.epam.courses.paycom.stub.PaymentStub;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
@ExtendWith(SpringExtension.class)

@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/root-context.xml", "classpath:web-spring-test.xml"})

public class PaymentControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private PaymentService paymentService;

    private MockMvc mockMvc;

    private int ZERO = 0;
    private int ONE = 1;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void findAllStubs() throws Exception {
        Mockito.when(paymentService.findAllStubs()).thenReturn(Arrays.asList(createStub(ZERO), createStub(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("payments"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>payments</title>")))
        ;
        Mockito.verify(paymentService, Mockito.times(1)).findAllStubs();
    }

    @Test
    public void findPaymentById() throws Exception {
        Mockito.when(paymentService.findById(anyInt())).thenReturn((create(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payment/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("payment"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>add payment</title>")))
        ;
        Mockito.verify(paymentService, Mockito.times(ONE)).findById(anyInt());
    }

    @Test
    public void addPayment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentId", "1")
                        .param("payerName", "name")
                        .param("paymentSum", "100")
                        .param("companyAccount", "account")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/payments"))
        ;

        Mockito.verify(paymentService, Mockito.times(ONE)).add(any());
    }

    @Test
    void DeleteCompany() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payment/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/payments"))
        ;

        Mockito.verify(paymentService, Mockito.times(ONE)).cancel(Mockito.anyInt());
    }



    private Payment create(int index) {
        Payment payment = new Payment();
        payment.setPaymentId(index);
        payment.setPayerName("name" + index);
        payment.setPaymentSum(100 + index);
        payment.setCompanyAccount("account" + index);
        payment.setPaymentDate(java.sql.Timestamp.valueOf("2019-03-10 12:12:30"));
        return payment;
    }

    private PaymentStub createStub(int index) {
        PaymentStub paymentStub = new PaymentStub();
        paymentStub.setId(index);
        paymentStub.setPayer("payer" + index);
        paymentStub.setSum(100 + index);
        paymentStub.setAccount("account" + index);
        paymentStub.setCompany("company" + index);
        paymentStub.setPayDate(java.sql.Timestamp.valueOf("2019-03-10 12:12:30"));
        return paymentStub;
    }

}
