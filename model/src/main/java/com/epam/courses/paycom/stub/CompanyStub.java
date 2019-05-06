package com.epam.courses.paycom.stub;

public class CompanyStub {

    private Integer id;

    private String company;

    private Integer count;

    private Integer amounts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyStub id(Integer id) {
        this.id = id;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public CompanyStub company(String company) {
        this.company = company;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CompanyStub count(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }

    public CompanyStub amounts(Integer amounts) {
        this.amounts = amounts;
        return this;
    }

    @Override
    public String toString() {
        return "CompanyStub{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", count='" + count + '\'' +
                ", amounts='" + amounts + '\'' +
                '}';
    }
}
