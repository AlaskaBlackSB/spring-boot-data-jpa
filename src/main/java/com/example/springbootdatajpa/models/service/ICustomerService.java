package com.example.springbootdatajpa.models.service;

import java.util.List;

import com.example.springbootdatajpa.models.entity.Customer;

public interface ICustomerService {
    
    public List<Customer> findAll();

    public void save(Customer customer);

    public Customer findById(Long id);

    public void deleteById(Long id);

}