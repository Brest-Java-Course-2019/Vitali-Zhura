package com.epam.courses.paycom.web_app.consumers;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.service.CompanyService;
import com.epam.courses.paycom.stub.CompanyStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class CompanyRestConsumer implements CompanyService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRestConsumer.class);

    private String url;

    private RestTemplate restTemplate;

    public CompanyRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Company> findAll() {
        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all", List.class);
        return (List<Company>) responseEntity.getBody();
    }

    @Override
    public  List<CompanyStub> findAllStubs() {
        LOGGER.debug("findAllStubs({})");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/stub", List.class);
        return (List<CompanyStub>) responseEntity.getBody();
    }

    @Override
    public Company findById(Integer id) {
        LOGGER.debug("findById({})", id);
        ResponseEntity<Company> responseEntity = restTemplate.getForEntity(url + "/" + id, Company.class);
        return responseEntity.getBody();
    }

    @Override
    public Company findByAccount(String account) {
        LOGGER.debug("findByAccount({})", account);
        ResponseEntity<Company> responseEntity = restTemplate.getForEntity(url + "/account/" + account, Company.class);
        return responseEntity.getBody();
    }

    @Override
    public void add(Company company) {
        LOGGER.debug("add({})", company);
        restTemplate.postForEntity(url, company, Company.class);
    }

    @Override
    public void update(Company company) {
        LOGGER.debug("update({})", company);
        restTemplate.put(url, company);
    }

    @Override
    public void delete(int id) {
        LOGGER.debug("update({})", id);
        restTemplate.delete(url + "/" + id);
    }
}
