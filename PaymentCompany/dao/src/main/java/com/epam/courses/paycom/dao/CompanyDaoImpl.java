package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CompanyDaoImpl implements CompanyDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);
    private static final String COMPANY_ID = "companyId";
    private static final String COMPANY_ACCOUNT = "companyAccount";
    private static final String COMPANY_NAME = "companyName";
    private static final String CHECK_COUNT_NAME = "select count(companyId) from company where lower(companyAccount) = lower(:companyAccount)";
    private static final String INSERT = "insert into company (companyAccount, companyName) values (:companyAccount, :companyName)";
    private static final String UPDATE = "update company set companyAccount = :companyAccount, companyName = :companyName where companyId = :companyId";
    private static final String DELETE = "delete from company where companyId = :companyId";
    private static final String SELECT_ALL = "select companyId, companyAccount, companyName from company";
    private static final String SELECT_BY_ID = "select companyId, companyAccount, companyName from company where companyId=:companyId";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CompanyDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Stream<Company> findAll() {
        LOGGER.debug("findAll()");

        List<Company> departments = namedParameterJdbcTemplate.query(SELECT_ALL, new CompanyRowMapper());
        return departments.stream();
    }

    @Override
    public Optional<Company> findById(Integer companyId) {
        LOGGER.debug("findById()");

        SqlParameterSource parameterSource = new MapSqlParameterSource(COMPANY_ID, companyId);
        Company company = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, parameterSource,
                BeanPropertyRowMapper.newInstance(Company.class));
        return Optional.ofNullable(company);
    }


    @Override
    public Optional<Company> add(Company company) {
        LOGGER.debug("add({})", company);
        return Optional.of(company)
                .filter(this::isNameUnique)
                .map(this::insertCompany)
                .orElseThrow(() -> new IllegalArgumentException("Company with the same name already exsists in DB."));
    }

    @Override
    public Optional<Company> addCompany(Company company) {
        LOGGER.debug("add({})", company);
        return Optional.of(company)
                .map(this::insertCompany)
                .orElseThrow(() -> new IllegalArgumentException("Company with the same name already exsists in DB."));
    }

    private boolean isNameUnique(Company company) {
        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_NAME,
                new MapSqlParameterSource(COMPANY_ACCOUNT, company.getCompanyAccount()),
                Integer.class) == 0;
    }

    private Optional<Company> insertCompany(Company company) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COMPANY_ACCOUNT, company.getCompanyAccount());
        mapSqlParameterSource.addValue(COMPANY_NAME, company.getCompanyName());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add( result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        company.setCompanyId(generatedKeyHolder.getKey().intValue());
        return Optional.of(company);
    }


    private class CompanyRowMapper implements RowMapper<Company>{

        @Override
        public Company mapRow(ResultSet resultSet, int i) throws SQLException {
            Company company = new Company();
            company.setCompanyId(resultSet.getInt(COMPANY_ID));
            company.setCompanyAccount(resultSet.getString(COMPANY_ACCOUNT));
            company.setCompanyName(resultSet.getString(COMPANY_NAME));

            return company;
        }
    }
    @Override
    public void update(Company company) {
        Optional.of(namedParameterJdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(company)))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to update department in DB"));
    }

    @Override
    public void delete(int companyId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COMPANY_ID, companyId);
        Optional.of(namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete department from DB"));
    }

    private boolean successfullyUpdated(int numRowsUpdated) {
        return numRowsUpdated > 0;
    }

}
