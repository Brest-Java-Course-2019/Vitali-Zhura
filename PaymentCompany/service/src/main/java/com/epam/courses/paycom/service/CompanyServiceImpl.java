package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.CompanyDao;
import com.epam.courses.paycom.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
public class CompanyServiceImpl implements CompanyService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyDao dao;

    public CompanyServiceImpl(CompanyDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Company> findAll() {
        LOGGER.debug("Find all companies");
        return dao.findAll().collect(Collectors.toList());
    }

    @Override
    public void add(Company company) {
            dao.addCompany(company);
        }

    @Override
    public Company findById(Integer id) {
        LOGGER.debug("findById({})", id);
        return dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to get company from DB"));
    }


    @Override
    public Company findByAccount(String companyAccount) {
        LOGGER.debug("findByAccount({})", companyAccount);
        return dao.findByAccount(companyAccount)
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
