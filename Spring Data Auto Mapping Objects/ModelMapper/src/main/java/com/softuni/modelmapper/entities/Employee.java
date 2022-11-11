package com.softuni.modelmapper.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private long id;

    private String firstName;

    private String lastName;

    private BigDecimal salary;

    private LocalDate birthday;

    private Address address;

    public Employee(){}

    public Employee(String firstName, BigDecimal salary, Address address) {
        this.firstName = firstName;
        this.salary = salary;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
