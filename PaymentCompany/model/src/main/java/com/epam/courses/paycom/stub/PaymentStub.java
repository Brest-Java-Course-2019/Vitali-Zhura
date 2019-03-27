package com.epam.courses.paycom.stub;

public class PaymentStub {

    private Integer paymentMax;

    private Integer paymentMin;

    private Integer paymentCount;

    private Double paymentAvg;

    public Integer getPaymentMax() {
        return paymentMax;
    }

    public void setPaymentMax(Integer paymentMax) {
        this.paymentMax = paymentMax;
    }

    public PaymentStub paymentMax(Integer paymentMax) {
        this.paymentMax = paymentMax;
        return this;
    }

    public Integer getPaymentMin() {
        return paymentMin;
    }

    public void setPaymentMin(Integer paymentMin) {
        this.paymentMin = paymentMin;
    }

    public PaymentStub paymentMin(Integer paymentMin) {
        this.paymentMin = paymentMin;
        return this;
    }

    public Integer getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }

    public PaymentStub paymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
        return this;
    }


    public Double getPaymentAvg() {
        return paymentAvg;
    }

    public void setPaymentAvg(Double paymentAvg) {
        this.paymentAvg = paymentAvg;
    }

    public PaymentStub paymentAvg(Double paymentAvg) {
        this.paymentAvg = paymentAvg;
        return this;
    }


    @Override
    public String toString() {
        return "DepartmentStub{" +
                "paymentMax=" + paymentMax +
                ", paymentMin='" + paymentMin + '\'' +
                ", paymentCount='" + paymentCount + '\'' +
                ", paymentAvg=" + paymentAvg +
                '}';
    }

}
