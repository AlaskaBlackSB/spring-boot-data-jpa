package com.example.springbootdatajpa.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.springbootdatajpa.models.entity.Customer;

// Marca la clase como componente de persistencia de acceso a datos
@Repository("CustomerDaoJPA")
public class CustomerDaoImplement implements ICustomerDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    // Lista todos los clientes
    public List<Customer> all() {
        // Se debe poner "Customer", asi como se llama la clase
        return em.createQuery("from Customer").getResultList();
    }

    @Override
    public void save(Customer customer) {

        //Si el cliente ya tiene id entonces lo edita
        if (customer.getId() != null && customer.getId() > 0) {
            // Toma el objeto Customer y lo actualiza en la bd
            em.merge(customer);
        }else{
            // Toma el objeto Customer y lo guarda en la bd
            em.persist(customer);
        }

    }

    @Override
    public Customer find(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    public void delete(Long id) {

        //Busca al Cliente
        Customer customer = find(id);

        //Elimina al cliente
        em.remove(customer);
    }

}