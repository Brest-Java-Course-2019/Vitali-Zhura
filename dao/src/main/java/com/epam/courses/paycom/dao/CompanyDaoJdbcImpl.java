package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.stub.CompanyStub;
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

public class CompanyDaoJdbcImpl implements CompanyDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoJdbcImpl.class);
    private static final String COMPANY_ID = "companyId";
    private static final String COMPANY_ACCOUNT = "companyAccount";
    private static final String COMPANY_NAME = "companyName";
    private static final String COMPANY_UNP = "companyUNP";
    private static final String INSERT = "insert into company (companyAccount, companyName, companyUNP) " +
            "values (:companyAccount, :companyName, :companyUNP)";
    private static final String UPDATE = "update company set companyAccount = :companyAccount, " +
            "companyName = :companyName, companyUNP = :companyUNP where companyId = :companyId";
    private static final String DELETE = "delete from company where companyId = :companyId";
    private static final String SELECT_ALL = "select companyId, companyAccount, companyName, companyUNP " +
            "from company Order BY companyId";
    private static final String SELECT_BY_ID = "select companyId, companyAccount, companyName, companyUNP from company " +
            "where companyId=:companyId";
    private static final String SELECT_BY_ACCOUNT = "select companyId, companyAccount, companyName, companyUNP " +
            "from company where companyAccount=:companyAccount";
    public static final String SELECT_ALL_STUBS = "SELECT c.companyId, c.companyName, IFNULL (COUNT(p.companyAccount),0)" +
            " AS Counts,IFNULL (SUM(p.paymentSum),0) AS Amounts" +
            " FROM company c LEFT JOIN payment p ON (c.companyAccount = p.companyAccount)" +
            " GROUP BY c.companyName Order BY companyId";
    public static final String COUNTS = "Counts";
    public static final String AMOUNTS = "Amounts";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CompanyDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Stream<Company> findAll() {
        LOGGER.debug("findAll()");

        List<Company> companies = namedParameterJdbcTemplate.query(SELECT_ALL, new CompanyRowMapper());
        return companies.stream();
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
    public Optional <Company> findByAccount(String companyAccount) {
        LOGGER.debug("findByAccount()");

        SqlParameterSource parameterSource = new MapSqlParameterSource(COMPANY_ACCOUNT, companyAccount);
        Company company = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ACCOUNT, parameterSource,
                BeanPropertyRowMapper.newInstance(Company.class));
        return Optional.ofNullable(company);
    }

    @Override
    public Stream<CompanyStub> findAllStubs() {
        LOGGER.debug("findAllStubs()");
        List<CompanyStub> companyList =
                namedParameterJdbcTemplate
                        .query(SELECT_ALL_STUBS,
                                (resultSet, i) -> new CompanyStub()
                                        .id(resultSet.getInt(COMPANY_ID))
                                        .company(resultSet.getString(COMPANY_NAME))
                                        .count(resultSet.getInt(COUNTS))
                                        .amounts(resultSet.getInt(AMOUNTS)));
        return companyList.stream();
    }

    @Override
    public Optional<Company> add(Company company) {
        LOGGER.debug("add({})", company);
        return Optional.of(company)
                .map(this::insertCompany)
                .orElseThrow(() -> new IllegalArgumentException("Company with the same name already exsists in DB."));
    }

    private Optional<Company> insertCompany(Company company) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COMPANY_ACCOUNT, company.getCompanyAccount());
        mapSqlParameterSource.addValue(COMPANY_NAME, company.getCompanyName());
        mapSqlParameterSource.addValue(COMPANY_UNP, company.getCompanyUNP());

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
            company.setCompanyUNP(resultSet.getString(COMPANY_UNP));
            return company;
        }
    }

    @Override
    public void update(Company company) {
        Optional.of(namedParameterJdbcTemplate.update(UPDATE, new BeanPropertySqlParameterSource(company)))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to update company in DB"));
    }

    @Override
    public void delete(int companyId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COMPANY_ID, companyId);
        Optional.of(namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to delete company from DB"));
    }

    private boolean successfullyUpdated(int numRowsUpdated) {
        return numRowsUpdated > 0;
    }
}
