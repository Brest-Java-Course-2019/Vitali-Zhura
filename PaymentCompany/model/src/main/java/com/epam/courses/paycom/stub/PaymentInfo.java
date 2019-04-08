package com.epam.courses.paycom.stub;

public class PaymentInfo {

    private Integer paymentMax;

    private Integer paymentMin;

    private Integer paymentsCount;

    private Integer paymentsSum;

    public Integer getPaymentMax() {
        return paymentMax;
    }

    public void setPaymentMax(Integer paymentMax) {
        this.paymentMax = paymentMax;
    }

    public PaymentInfo paymentMax(Integer paymentMax) {
        this.paymentMax = paymentMax;
        return this;
    }

    public Integer getPaymentMin() {
        return paymentMin;
    }

    public void setPaymentMin(Integer paymentMin) {
        this.paymentMin = paymentMin;
    }

    public PaymentInfo paymentMin(Integer paymentMin) {
        this.paymentMin = paymentMin;
        return this;
    }

    public Integer getPaymentsCount() {
        return paymentsCount;
    }

    public void setPaymentsCount(Integer paymentsCount) {
        this.paymentsCount = paymentsCount;
    }

    public PaymentInfo paymentsCount(Integer paymentsCount) {
        this.paymentsCount = paymentsCount;
        return this;
    }


    public Integer getPaymentsSum() {
        return paymentsSum;
    }

    public void setPaymentsSum(Integer paymentsSum) {
        this.paymentsSum = paymentsSum;
    }

    public PaymentInfo paymentsSum(Integer paymentsSum) {
        this.paymentsSum = paymentsSum;
        return this;
    }


    @Override
    public String toString() {
        return "DepartmentStub{" +
                "paymentMax=" + paymentMax +
                ", paymentMin='" + paymentMin + '\'' +
                ", paymentsCount='" + paymentsCount + '\'' +
                ", paymentsSum=" + paymentsSum +
                '}';
    }

}
