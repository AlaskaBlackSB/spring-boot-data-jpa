package com.example.springbootdatajpa.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springbootdatajpa.models.entity.Customer;
import com.example.springbootdatajpa.models.service.ICustomerService;

@Controller
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping({ "/all" })
    public String all(Model model) {

        model.addAttribute("title", "Customers list");
        model.addAttribute("customers", customerService.all());

        return "all";
    }

    @GetMapping({ "/form" })
    public String create(Model model) {

        Customer customer = new Customer();

        model.addAttribute("title", "Create Customer");
        model.addAttribute("txtButton", "Create");
        model.addAttribute("customer", customer);

        return "form";
    }

    @PostMapping({ "/form" })
    public String store(@Valid Customer customer, BindingResult resutl, Model model) {

        // Comprueba si hay errores de validacion
        if (resutl.hasErrors()) {
            // No es necesario pasar el modelo Costumer porque se pasa automaticamente ya
            // que la clase y el modelo tienen el mismo nombre <Customer> y <customer>
            model.addAttribute("title", "Create Customer");
            model.addAttribute("txtButton", "Create");
            return "form";
        }

        // Guarda el cliente en la base de datos
        customerService.save(customer);

        return "redirect:all";
    }

    @GetMapping({ "/form/{id}" })
    public String edit(@PathVariable(value = "id") Long id, Model model) {

        Customer customer = null;

        // Comprueba que el id sea valido
        if (id < 1) {
            return "redirect:/all";
        }

        customer = customerService.find(id);

        // Comprueba que el cliente exista
        if (customer == null) {
            return "redirect:/all";
        }

        model.addAttribute("title", "Edit Customer");
        model.addAttribute("txtButton", "Edit");
        model.addAttribute("customer", customer);

        return "form";
    }

    @GetMapping({ "/delete/{id}" })
    public String delete(@PathVariable(value = "id") Long id) {

        // Comprueba que el id sea valido
        if (id < 1) {
            return "redirect:/all";
        }

        Customer customer = customerService.find(id);

        // Comprueba que el cliente exista
        if (customer == null) {
            return "redirect:/all";
        }

        //Elimina el cliente
        customerService.delete(id);

        return "redirect:/all";
    }

}