package com.epam.courses.paycom.rest_app;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(value = "/companies")
public class CompanyRestController implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestController.class);


    @Autowired
    private CompanyService companyService;


    @Override
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Company> findAll() {
        LOGGER.debug("get all companies");
        return companyService.findAll();
    }


    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Company findById(@PathVariable Integer id) {
        LOGGER.debug("find department by id({})", id);
        return companyService.findById(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public Company findByAccount(@PathVariable String account) {
        LOGGER.debug("find company by account({})", account);
        return companyService.findByAccount(account);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Company company) {
        LOGGER.debug("add company({})", company);
        companyService.add(company);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody Company company) {
        LOGGER.debug("update company ({})", company);
        companyService.update(company);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        LOGGER.debug("delete company ({})", id);
        companyService.delete(id);
    }

}