package com.epam.courses.paycom.model;

import java.util.Date;


public class Payment {

    private Integer paymentId;

    private String payerName;

    private Integer paymentSum;

    private String companyAccount;

    private Date paymentDate;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Integer getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(Integer paymentSum) {
        this.paymentSum = paymentSum;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }


    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", payerName='" + payerName + '\'' +
                ", paymentSum='" + paymentSum + '\'' +
                ", companyAccount='" + companyAccount + '\'' +
                ", paymentData='" + paymentDate + '\'' +
                '}';
    }

}
