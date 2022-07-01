package com.example.springbootdatajpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springbootdatajpa.models.dao.ICustomerDao;

@Controller
public class CustomerController {

    @Autowired
    @Qualifier("CustomerDaoJPA")
    private ICustomerDao customerDao;

    @GetMapping({ "/all" })
    public String index(Model model) {

        model.addAttribute("title", "Customers list");
        model.addAttribute("customers", customerDao.all());

        return "all";
    }

}