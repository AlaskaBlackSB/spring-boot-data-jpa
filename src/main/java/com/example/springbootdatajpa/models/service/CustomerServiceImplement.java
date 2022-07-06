package com.example.springbootdatajpa.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootdatajpa.models.dao.ICustomerDao;
import com.example.springbootdatajpa.models.entity.Customer;

@Service
public class CustomerServiceImplement implements ICustomerService{
    
    @Autowired
    private ICustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll(){
        return (List<Customer>) customerDao.findAll();
    }

    @Override
    @Transactional
    public void save(Customer customer){
        customerDao.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(Long id){
        // Si no encuentra el cliente retorna un null
        return customerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        customerDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        return customerDao.findAll(pageable);
    }

}