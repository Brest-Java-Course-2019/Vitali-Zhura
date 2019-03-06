package com.epam.courses.paycom.service;


import com.epam.courses.paycom.dao.CompanyDao;
import com.epam.courses.paycom.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Transactional
public class CompanyServiceImpl implements CompanyService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyDao dao;

    public CompanyServiceImpl(CompanyDao dao) {
        this.dao = dao;
    }

    @Override
    public Stream<Company> findAll() {
        LOGGER.debug("Find all companies");
        return dao.findAll();
    }

    @Override
    public void add(Company... companies) {
        for (Company company : companies) {
            dao.addCompany(company);
        }
    }

    @Override
    public Company findById(Integer id) {
        LOGGER.debug("findById({})", id);
        return dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to get company from DB"));
    }

    @Override
    public void update(Company company) {
        LOGGER.debug("update({})", company);
        dao.update(company);
    }

    @Override
    public void delete (int id) {
        LOGGER.debug("delete({})", id);
        dao.delete(id);
    }
}
