package com.example.springbootdatajpa.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootdatajpa.models.entity.Customer;
import com.example.springbootdatajpa.models.service.ICustomerService;
import com.example.springbootdatajpa.util.paginator.PageRender;

@Controller
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping({ "/all" })
    public String all(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model) {

        //Obtiene 4 clientes por pagina
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Customer> customers = customerService.findAll(pageRequest);
        PageRender<Customer> pageRender = new PageRender<>("/all", customers);

        model.addAttribute("title", "Customers list");
        model.addAttribute("customers", customers);
        model.addAttribute("page", pageRender);

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
    public String store(@Valid Customer customer, BindingResult resutl, Model model, RedirectAttributes flash) {

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

        flash.addFlashAttribute("success", "Cliente guardado con éxito");
        return "redirect:all";
    }

    @GetMapping({ "/form/{id}" })
    public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = null;

        // Comprueba que el id sea valido
        if (id < 1) {
            flash.addFlashAttribute("warning", "El ID del cliente es erróneo.");
            return "redirect:/all";
        }

        customer = customerService.findById(id);

        // Comprueba que el cliente exista
        if (customer == null) {
            flash.addFlashAttribute("error", "El cliente que se quiere editar no existe");
            return "redirect:/all";
        }

        model.addAttribute("title", "Edit Customer");
        model.addAttribute("txtButton", "Edit");
        model.addAttribute("customer", customer);

        return "form";
    }

    @GetMapping({ "/delete/{id}" })
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        // Comprueba que el id sea valido
        if (id < 1) {
            flash.addFlashAttribute("warning", "El ID del cliente es erróneo o no tiene un formato válido");
            return "redirect:/all";
        }

        Customer customer = customerService.findById(id);

        // Comprueba que el cliente exista
        if (customer == null) {
            flash.addFlashAttribute("warning", "El cliente que se quiere eliminar no existe");
            return "redirect:/all";
        }

        //Elimina el cliente
        customerService.deleteById(id);

        flash.addFlashAttribute("success", "Cliente eliminado con éxito");

        return "redirect:/all";
    }

}