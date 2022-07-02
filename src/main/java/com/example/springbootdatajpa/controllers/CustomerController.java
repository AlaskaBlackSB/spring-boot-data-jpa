package com.example.springbootdatajpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springbootdatajpa.models.dao.ICustomerDao;
import com.example.springbootdatajpa.models.entity.Customer;

@Controller
public class CustomerController {

    @Autowired
    @Qualifier("CustomerDaoJPA")
    private ICustomerDao customerDao;

    @GetMapping({ "/all" })
    public String all(Model model) {

        model.addAttribute("title", "Customers list");
        model.addAttribute("customers", customerDao.all());

        return "all";
    }

    @GetMapping({ "/form" })
    public String create(Model model) {

        Customer customer = new Customer();

        model.addAttribute("title", "Create Customer");
        model.addAttribute("customer", customer);

        return "form";
    }

    @PostMapping({ "/form" })
    public String store(Customer customer, Model model) {

        // Guarda el cliente en la base de datos
        customerDao.save(customer);

        return "redirect:all";
    }

}