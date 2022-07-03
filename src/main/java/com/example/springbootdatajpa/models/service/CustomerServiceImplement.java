package com.example.springbootdatajpa.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootdatajpa.models.dao.ICustomerDao;
import com.example.springbootdatajpa.models.entity.Customer;

@Service
public class CustomerServiceImplement implements ICustomerService{
    
    @Autowired
    @Qualifier("CustomerDaoJPA")
    private ICustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> all(){
        return customerDao.all();
    }

    @Override
    @Transactional
    public void save(Customer customer){
        customerDao.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer find(Long id){
        return customerDao.find(id);
    }

    @Override
    @Transactional
    public void delete(Long id){
        customerDao.delete(id);
    }

}