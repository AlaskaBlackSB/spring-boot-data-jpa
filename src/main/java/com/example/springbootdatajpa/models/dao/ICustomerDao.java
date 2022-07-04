package com.example.springbootdatajpa.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.springbootdatajpa.models.entity.Customer;

// No se anota con repository o com-ponent porque ya es un componente de 
// Spring porque ya se hereda de CrudRepository
public interface ICustomerDao extends CrudRepository<Customer, Long> {

}