package com.epam.courses.paycom.model;


public class Payment {

    private Integer paymentId;

    private String paymentDescription;

    private Integer paymentSumm;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public Integer getPaymentSumm() {
        return paymentSumm;
    }

    public void setPaymentSumm(Integer paymentSumm) {
        this.paymentSumm = paymentSumm;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentDescription='" + paymentDescription + '\'' +
                ", paymentSumm='" + paymentSumm + '\'' +
                '}';
    }

}
