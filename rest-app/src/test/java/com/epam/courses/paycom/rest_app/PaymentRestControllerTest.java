package com.epam.courses.paycom.rest_app;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.service.PaymentService;
import com.epam.courses.paycom.stub.PaymentStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:rest-spring-test.xml"})

public class PaymentRestControllerTest {

    @Autowired
    private PaymentRestController controller;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

   DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private MockMvc mockMvc;

    private int ZERO = 0;
    private int ONE = 1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @AfterEach
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(paymentService);
        Mockito.reset(paymentService);
    }

    @Test
    public void findAllPayments() throws Exception {
        Mockito.when(paymentService.findAll()).thenReturn(new ArrayList<Payment>() {{
            add(create(ZERO));
            add(create(ONE));
        }});

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payments/all")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].payerName", Matchers.is("name0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentSum", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAccount", Matchers.is("account0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentDate", Matchers.is(formatter.parse("2019-03-10 12:12:30").getTime())))

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].payerName", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentSum", Matchers.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAccount", Matchers.is("account1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentDate", Matchers.is(formatter.parse("2019-03-10 12:12:30").getTime())));

        Mockito.verify(paymentService, Mockito.times(ONE)).findAll();
    }

    @Test
    public void findAllStubs() throws Exception {
        Mockito.when(paymentService.findAllStubs()).thenReturn(new ArrayList<PaymentStub>() {{
            add(createStub(ZERO));
            add(createStub(ONE));
        }});

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payments/stub")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].payer", Matchers.is("payer0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sum", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account", Matchers.is("account0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].company", Matchers.is("company0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].payDate", Matchers.is(formatter.parse("2019-03-10 12:12:30").getTime())))

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].payer", Matchers.is("payer1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sum", Matchers.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].account", Matchers.is("account1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].company", Matchers.is("company1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].payDate", Matchers.is(formatter.parse("2019-03-10 12:12:30").getTime())));

        Mockito.verify(paymentService, Mockito.times(ONE)).findAllStubs();
    }

    @Test
    public void findPaymentById() throws Exception {
        Mockito.when(paymentService.findById(anyInt())).thenReturn(create(1));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payments/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payerName", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSum", Matchers.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyAccount", Matchers.is("account1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentDate", Matchers.is(formatter.parse("2019-03-10 12:12:30").getTime())));

        Mockito.verify(paymentService, Mockito.times(ONE)).findById(anyInt());
    }

    @Test
    public void addPayment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/payments")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(create(1)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(paymentService, Mockito.times(ONE)).add(any());
    }

    @Test
    public void cancelPayment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/payments/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(create(1)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(paymentService, Mockito.times(ONE)).cancel(anyInt());
    }

    @Test
    public void findPaymentByDate() throws Exception {
        Mockito.when(paymentService.findByDate(any(), any())).thenReturn(new ArrayList<Payment>() {{
            add(create(ZERO));
            add(create(ONE));
        }});

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payments/2019-03-10/2019-03-11")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].payerName", Matchers.is("name0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentSum", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAccount", Matchers.is("account0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentDate", Matchers.is(formatter.parse("2019-03-10 12:12:30").getTime())))

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].payerName", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentSum", Matchers.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAccount", Matchers.is("account1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentDate", Matchers.is(formatter.parse("2019-03-10 12:12:30").getTime())));

        Mockito.verify(paymentService, Mockito.times(ONE)).findByDate(any(), any());
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