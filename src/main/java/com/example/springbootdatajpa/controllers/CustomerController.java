package com.example.springbootdatajpa.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import com.example.springbootdatajpa.service.IUploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootdatajpa.models.entity.Customer;
import com.example.springbootdatajpa.models.service.ICustomerService;
import com.example.springbootdatajpa.util.paginator.PageRender;

@Controller
@Slf4j
@SessionAttributes("customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IUploadFileService uploadFileService;

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

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> showPhoto(@PathVariable String filename){

        Resource resource = null;

        try{
            resource = uploadFileService.load(filename);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping({ "/show/{id}" })
    public String show(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {

        Customer customer = customerService.findById(id);

        // Comprueba que el cliente exista
        if (customer == null) {
            flash.addFlashAttribute("error", "El cliente que se quiere editar no existe");
            return "redirect:/all";
        }

        model.addAttribute("title", "Detalle del cliente: ".concat(customer.getName()));
        model.addAttribute("customer", customer);

        return "show";
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
    public String store(@Valid Customer customer, BindingResult resutl, Model model, @RequestParam("file") MultipartFile photo, RedirectAttributes flash) {

        // Comprueba si hay errores de validacion
        if (resutl.hasErrors()) {
            // No es necesario pasar el modelo Costumer porque se pasa automaticamente ya
            // que la clase y el modelo tienen el mismo nombre <Customer> y <customer>
            model.addAttribute("title", "Create Customer");
            model.addAttribute("txtButton", "Create");

            return "form";
        }

        // Comprueba si la foto no esta vacía para guardarla
        if (!photo.isEmpty()) {

            // Elimina la foto anterior
            if(
                    customer.getId() != null             // Comprueba que el usuario exista
                    && customer.getId() > 0              // Comprueba que el id del usuario sea válido
                    && customer.getPhoto() != null       // Comprueba que el usuario ya tenga una foto
                    && customer.getPhoto().length() > 0  // Comprueba que la foto tenga un nombre (por si acaso)
            ){
                uploadFileService.delete(customer.getPhoto());
            }

            String uniqueFilename = "";
            try {
                // Copia la imagen a la carpeta uploads
                uniqueFilename = uploadFileService.copy(photo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Agrega el mensaje que será mostrado al usuario
            flash.addFlashAttribute("info", "Se ha subido correctamente la foto.");

            // Asigna el nombre de la foto al atributo del cliente
            customer.setPhoto(uniqueFilename);
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
            flash.addFlashAttribute("warning", "El ID del cliente es erróneo o no tiene " +
                    "un formato válido");
            return "redirect:/all";
        }

        Customer customer = customerService.findById(id);

        // Comprueba que el cliente exista
        if (customer == null) {
            flash.addFlashAttribute("warning", "El cliente que se quiere eliminar no existe");
            return "redirect:/all";
        }

        // Elimina la imagen del cliente en el servidor
        if (uploadFileService.delete(customer.getPhoto())){
            flash.addFlashAttribute("info", "Se ha eliminado la imagen del cliente");
        }

        //Elimina el cliente
        customerService.deleteById(id);

        flash.addFlashAttribute("success", "Cliente eliminado con éxito");

        return "redirect:/all";
    }

}