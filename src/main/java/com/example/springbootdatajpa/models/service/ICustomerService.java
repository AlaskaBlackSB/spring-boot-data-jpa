package com.example.springbootdatajpa.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.springbootdatajpa.models.entity.Customer;

public interface ICustomerService {
    
    public List<Customer> findAll();

    public Page<Customer> findAll(Pageable pageable);

    public void save(Customer customer);

    public Customer findById(Long id);

    public void deleteById(Long id);

}