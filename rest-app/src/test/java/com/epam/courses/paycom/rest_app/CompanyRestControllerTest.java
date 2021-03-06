package com.epam.courses.paycom.rest_app;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.service.CompanyService;
import com.epam.courses.paycom.stub.CompanyStub;
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
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:rest-spring-test.xml"})


public class CompanyRestControllerTest {

    @Autowired
    private CompanyRestController controller;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

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
        Mockito.verifyNoMoreInteractions(companyService);
        Mockito.reset(companyService);
    }

    @Test
    public void findAll() throws Exception {
        Mockito.when(companyService.findAll()).thenReturn(new ArrayList<Company>() {{add(create(ZERO)); add(create(ONE));}});

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/all")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAccount", Matchers.is("account0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName", Matchers.is("name0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyUNP", Matchers.is("111111110")))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAccount", Matchers.is("account1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyUNP", Matchers.is("111111111")))
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).findAll();
    }

    @Test
    public void findAllStubs() throws Exception {
        Mockito.when(companyService.findAllStubs()).thenReturn(new ArrayList<CompanyStub>() {{
            add(createStub(ZERO));
            add(createStub(ONE));
        }});

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/stub")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].company", Matchers.is("company0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].count", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amounts", Matchers.is(200)))

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].company", Matchers.is("company1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].count", Matchers.is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amounts", Matchers.is(201)));

        Mockito.verify(companyService, Mockito.times(ONE)).findAllStubs();
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(companyService.findById(anyInt())).thenReturn(create(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyAccount", Matchers.is("account1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyUNP", Matchers.is("111111111")))
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).findById(anyInt());
    }

    @Test
    public void findByAccount() throws Exception {
        Mockito.when(companyService.findByAccount(anyString())).thenReturn(create(ONE));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/account/account")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyAccount", Matchers.is("account1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyUNP", Matchers.is("111111111")))
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).findByAccount(anyString());
    }

    @Test
    public void add() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(create(1)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).add(any());
    }

    @Test
    public void update() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/companies")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(create(1)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).update(any());
    }

    @Test
    public void delete() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/companies/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(create(1)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).delete(anyInt());
    }

    private Company create(int index) {
        Company company = new Company();
        company.setCompanyId(index);
        company.setCompanyAccount("account" + index);
        company.setCompanyName("name" + index);
        company.setCompanyUNP("11111111" + index);
        return company;
    }

    private CompanyStub createStub(int index) {
        CompanyStub companyStub = new CompanyStub();
        companyStub.setId(index);
        companyStub.setCompany("company" + index);
        companyStub.setCount(5 + index);
        companyStub.setAmounts(200 + index);
        return companyStub;
    }
}
