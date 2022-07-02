package com.example.springbootdatajpa.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootdatajpa.models.entity.Customer;

// Marca la clase como componente de persistencia de acceso a datos
@Repository("CustomerDaoJPA")
public class CustomerDaoImplement implements ICustomerDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)

    // Lista todos los clientes
    public List<Customer> all() {
        // Se debe poner "Customer", asi como se llama la clase
        return em.createQuery("from Customer").getResultList();
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        // Toma el objeto Customer y lo guarda en la tabla
        em.persist(customer);
    }

}