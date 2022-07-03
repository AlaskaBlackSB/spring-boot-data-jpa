package com.example.springbootdatajpa.models.service;

import java.util.List;

import com.example.springbootdatajpa.models.entity.Customer;

public interface ICustomerService {
    
    public List<Customer> all();

    public void save(Customer customer);

    public Customer find(Long id);

    public void delete(Long id);

}