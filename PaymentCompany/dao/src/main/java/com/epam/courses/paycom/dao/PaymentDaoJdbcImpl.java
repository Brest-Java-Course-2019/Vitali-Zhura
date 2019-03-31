package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Payment;
import com.epam.courses.paycom.stub.PaymentInfo;

import com.epam.courses.paycom.stub.PaymentStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.time.LocalDateTime;

public class PaymentDaoJdbcImpl implements PaymentDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoJdbcImpl.class);
    private static final String PAYMENT_ID = "paymentId";
    private static final String PAYER_NAME = "payerName";
    private static final String PAYMENT_SUM = "paymentSum";
    private static final String COMPANY_ACCOUNT = "companyAccount";
    private static final String COMPANY_NAME = "companyName";
    private static final String PAYMENT_DATE = "paymentDate";
    private static final String BEGIN_DATE = "beginDate";
    private static final String END_DATE = "endDate";
    private static final String INSERT = "insert into payment (payerName, paymentSum, companyAccount) values (:payerName, :paymentSum, :companyAccount)";
    private static final String CANCEL = "delete from payment where paymentId = :paymentId";
    private static final String SELECT_ALL = "select paymentId, payerName, paymentSum, companyAccount, paymentDate from payment";
    private static final String SELECT_BY_ID = "select paymentId, payerName, paymentSum, companyAccount, paymentDate from payment where paymentId=:paymentId";
    private static final String SELECT_BY_DATE = "select * from payment where paymentDate >= :beginDate AND paymentDate < (Select DATEADD (day, 1, :endDate))";

    private static final String SELECT_ALL_INFO = "select MAX (paymentSum) as paymentMax, MIN (paymentSum) as paymentMin, count (paymentSum) as paymentCount, AVG (paymentSum) as paymentAvg from payment";
    public static final String SELECT_ALL_STUBS = "SELECT p.paymentId, p.payerName, p.paymentSum, p.companyAccount," +
            "c.companyName, p.paymentDate FROM payment p INNER JOIN company c ON (p.companyAccount = c.companyAccount)";

    private static final String PAYMENT_MAX = "paymentMax";
    private static final String PAYMENT_MIN = "paymentMin";
    private static final String PAYMENT_COUNT = "paymentCount";
    private static final String PAYMENT_AVG = "paymentAvg";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PaymentDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Stream<Payment> findAll() {
        LOGGER.debug("findAll()");

        List<Payment> payments= namedParameterJdbcTemplate.query(SELECT_ALL, new CompanyRowMapper());
        return payments.stream();
    }

    @Override
    public Stream<PaymentStub> findAllStubs() {
        LOGGER.debug("findAllStubs()");
        List<PaymentStub> paymentList =
                namedParameterJdbcTemplate
                        .query(SELECT_ALL_STUBS,
                                (resultSet, i) -> new PaymentStub()
                                        .id(resultSet.getInt(PAYMENT_ID))
                                        .payer(resultSet.getString(PAYER_NAME))
                                        .sum(resultSet.getInt(PAYMENT_SUM))
                                        .account(resultSet.getString(COMPANY_ACCOUNT))
                                        .company(resultSet.getString(COMPANY_NAME))
                                        .payDate(resultSet.getDate(PAYMENT_DATE)));
        return paymentList.stream();
    }

    @Override
    public Optional<Payment> findById(Integer paymentId) {
        LOGGER.debug("findById()");

        SqlParameterSource parameterSource = new MapSqlParameterSource(PAYMENT_ID, paymentId);
        Payment payment = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, parameterSource,
                BeanPropertyRowMapper.newInstance(Payment.class));
        return Optional.ofNullable(payment);
    }

    @Override
    public Stream <Payment> findByDate(Date beginDate, Date endDate) {
        LOGGER.debug("findByDate()");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue(BEGIN_DATE, beginDate, Types.DATE);
        parameterSource.addValue(END_DATE, endDate, Types.DATE);

        List <Payment> payments = namedParameterJdbcTemplate.query(SELECT_BY_DATE, parameterSource,
                BeanPropertyRowMapper.newInstance(Payment.class));
        return payments.stream();
    }



    @Override
    public Optional<Payment> add(Payment payment) {
        LOGGER.debug("add({})", payment);
        return Optional.of(payment)
                .map(this::insertPayment)
                .orElseThrow(() -> new IllegalArgumentException("Company with the same name already exsists in DB."));
    }



    private Optional<Payment> insertPayment(Payment payment) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(PAYER_NAME, payment.getPayerName());
        mapSqlParameterSource.addValue(PAYMENT_SUM, payment.getPaymentSum());
        mapSqlParameterSource.addValue(COMPANY_ACCOUNT, payment.getCompanyAccount());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, generatedKeyHolder);
        LOGGER.debug("add( result update = {}, keyholder = {})", result, generatedKeyHolder.getKey().intValue());

        payment.setPaymentId(generatedKeyHolder.getKey().intValue());

        LocalDateTime localDate = LocalDateTime.now();

        payment.setPaymentDate(java.sql.Timestamp.valueOf(localDate));
        return Optional.of(payment);
    }


    private class CompanyRowMapper implements RowMapper<Payment>{

        @Override
        public Payment mapRow(ResultSet resultSet, int i) throws SQLException {
            Payment payment = new Payment();
            payment.setPaymentId(resultSet.getInt(PAYMENT_ID));
            payment.setPayerName(resultSet.getString(PAYER_NAME));
            payment.setPaymentSum(resultSet.getInt(PAYMENT_SUM));
            payment.setCompanyAccount(resultSet.getString(COMPANY_ACCOUNT));
            payment.setPaymentDate(resultSet.getTimestamp(PAYMENT_DATE));

            return payment;
        }
    }


    @Override
    public void cancel (int paymentId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(PAYMENT_ID, paymentId);
        Optional.of(namedParameterJdbcTemplate.update(CANCEL, mapSqlParameterSource))
                .filter(this::successfullyUpdated)
                .orElseThrow(() -> new RuntimeException("Failed to cancel payment from DB"));
    }

    private boolean successfullyUpdated(int numRowsUpdated) {
        return numRowsUpdated > 0;
    }

    @Override
    public Stream<PaymentInfo> findAllInfo() {
        LOGGER.debug("findAllInfo()");
        List<PaymentInfo> paymentInfo =
                namedParameterJdbcTemplate
                        .query(SELECT_ALL_INFO,
                                (resultSet, i) -> new PaymentInfo()
                                        .paymentMax(resultSet.getInt(PAYMENT_MAX))
                                        .paymentMin(resultSet.getInt(PAYMENT_MIN))
                                        .paymentCount(resultSet.getInt(PAYMENT_COUNT))
                                        .paymentAvg(resultSet.getDouble(PAYMENT_AVG)));
        return paymentInfo.stream();
    }


}
