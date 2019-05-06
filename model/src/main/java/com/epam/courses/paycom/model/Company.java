package com.epam.courses.paycom.model;


public class Company {

    private Integer companyId;

    private String companyAccount;

    private String companyName;

    private String companyUNP;


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUNP() {
        return companyUNP;
    }

    public void setCompanyUNP(String companyUNP) {
        this.companyUNP = companyUNP;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyAccount='" + companyAccount + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyUNP='" + companyUNP + '\'' +
                '}';
    }
}
