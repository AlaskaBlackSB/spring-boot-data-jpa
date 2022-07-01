package com.example.springbootdatajpa.models.dao;

import java.util.List;

import com.example.springbootdatajpa.models.entity.Customer;

public interface ICustomerDao {

    public List<Customer> all();

}