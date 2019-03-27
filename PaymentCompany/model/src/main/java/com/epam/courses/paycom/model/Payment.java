package com.epam.courses.paycom.model;

import java.util.Date;


public class Payment {

    private Integer paymentId;

    private String payerName;

    private String paymentDescription;

    private Integer paymentSum;

    private Integer companyId;

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

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public Integer getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(Integer paymentSum) {
        this.paymentSum = paymentSum;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
                ", paymentDescription='" + paymentDescription + '\'' +
                ", paymentSum='" + paymentSum + '\'' +
                ", companyId='" + companyId + '\'' +
                ", paymentData='" + paymentDate + '\'' +
                '}';
    }

}
