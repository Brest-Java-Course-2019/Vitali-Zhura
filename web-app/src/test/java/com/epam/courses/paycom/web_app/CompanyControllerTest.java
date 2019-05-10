package com.epam.courses.paycom.web_app;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.service.CompanyService;
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


public class CompanyControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CompanyService companyService;

    private MockMvc mockMvc;

    private int ZERO = 0;
    private int ONE = 1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void findAll() throws Exception {
        Mockito.when(companyService.findAll()).thenReturn(Arrays.asList(create(ZERO), create(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("companies"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>companies</title>")))
        ;
        Mockito.verify(companyService, Mockito.times(ONE)).findAll();
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(companyService.findById(anyInt())).thenReturn((create(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/company/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("company"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>add company</title>")))
        ;
        Mockito.verify(companyService, Mockito.times(ONE)).findById(anyInt());
    }

    @Test
    public void findAllByAccount() throws Exception {
        Mockito.when(companyService.findByAccount(anyString())).thenReturn((create(ONE)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/account")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("companies"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                        containsString("<title>companies</title>")))
        ;
        Mockito.verify(companyService, Mockito.times(ONE)).findByAccount(anyString());
    }

    @Test
    public void addCompany() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("companyId", "5")
                        .param("companyAccount", "BY27BLBB37899638899498006077")
                        .param("companyName", "newcompany")
                        .param("companyUNP", "234000766")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/companies"))
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).add(any());
    }

    @Test
    public void updateCompany() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/company/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("companyId", "1")
                        .param("companyAccount", "BY27BLBB38800630217478006888")
                        .param("companyName", "newcompany")
                        .param("companyUNP", "234000766")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/companies"))
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).update(any());
    }

    @Test
    void DeleteCompany() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/company/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/companies"))
        ;

        Mockito.verify(companyService, Mockito.times(ONE)).delete(Mockito.anyInt());
    }

    private Company create(int index) {
        Company company = new Company();
        company.setCompanyId(index);
        company.setCompanyAccount("account" + index);
        company.setCompanyName("name" + index);
        company.setCompanyUNP("11111111" + index);
        return company;
    }

}
