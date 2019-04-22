package com.epam.courses.paycom.stub;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentStub {


    private Integer id;

    private String payer;

    private Integer sum;

    private String account;

    private String company;

    private Date payDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentStub id(Integer id) {
        this.id = id;
        return this;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public PaymentStub payer(String payer) {
        this.payer = payer;
        return this;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public PaymentStub sum(Integer sum) {
        this.sum = sum;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public PaymentStub account(String account) {
        this.account = account;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public PaymentStub company(String company) {
        this.company = company;
        return this;
    }


    public Date getPayDate() {
        return payDate;
    }


    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }


    public PaymentStub payDate(Date payDate) {
        this.payDate = payDate;
        return this;
    }

    @Override
    public String toString() {
        return "PaymentStub{" +
                "id=" + id +
                ", payer='" + payer + '\'' +
                ", sum='" + sum + '\'' +
                ", account='" + account + '\'' +
                ", company='" + company + '\'' +
                ", payDate='" + payDate + '\'' +
                '}';
    }

}



