package com.epam.courses.paycom.rest_app;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.service.PaymentService;

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

import java.util.ArrayList;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:rest-spring-test.xml"})



public class PaymentRestControllerTest {

    @Autowired
    private PaymentRestController controller;

    @Autowired
    private PaymentService paymentService;

    private MockMvc mockMvc;

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
    public void payments() throws Exception {
        Mockito.when(paymentService.findAll()).thenReturn(new ArrayList<Payment>() {{
            add(create(0));
            add(create(1));
        }});

        mockMvc.perform(
                MockMvcRequestBuilders.get("/payments/all")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].payerName", Matchers.is("name0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentDescription", Matchers.is("desc0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentSum", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentDate", Matchers.is(1552165200000L)))

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].payerName", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentDescription", Matchers.is("desc1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentSum", Matchers.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentDate", Matchers.is(1552165200000L)))
        ;

        Mockito.verify(paymentService, Mockito.times(1)).findAll();
    }

    private Payment create(int index) {
        Payment payment = new Payment();
        payment.setPaymentId(index);
        payment.setPayerName("name" + index);
        payment.setPaymentDescription("desc" + index);
        payment.setPaymentSum(100 + index);
        payment.setCompanyId(1 + index);
        payment.setPaymentDate(java.sql.Date.valueOf("2019-03-10"));
        return payment;
    }
}